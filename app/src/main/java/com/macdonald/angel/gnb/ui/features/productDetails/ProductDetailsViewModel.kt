package com.macdonald.angel.gnb.ui.features.productDetails

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.macdonald.angel.data.model.ProductDetailsModel
import com.macdonald.angel.data.model.RateModel
import com.macdonald.angel.data.model.TransactionDetailsModel
import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.gnb.common.ScopedViewModel
import com.macdonald.angel.gnb.data.toTransactionDetailsModelList
import com.macdonald.angel.gnb.ui.features.rateList.RatesController
import com.macdonald.angel.usecases.ProductsUseCases
import com.macdonald.angel.usecases.RatesUseCases
import com.macdonald.angel.usecases.TransactionsUseCases
import kotlinx.coroutines.*

class ProductDetailsViewModel(
    private val ctx: Application,
    private val productUseCases: ProductsUseCases,
    private val transactionsUseCases: TransactionsUseCases,
    private val ratesUseCases: RatesUseCases
) : ScopedViewModel(), ProductDetailsContract.ViewModel {


    private lateinit var getTransactionsByProductJob: Job
    private lateinit var updateProductDetailsJob: Job
    private lateinit var getRatesJob: Job

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        class ShowProductDetailsData(val productDetails: ProductDetailsModel) : UiModel()
        class UpdateProductDetails(val productDetails: ProductDetailsModel) : UiModel()


        object NotProductTransactionsFoundLocally : UiModel()
        object NotRateDataFoundLocally : UiModel()
        object ErrorUpdatingProductDetails : UiModel()
        object ErrorGettingTransactionsByProduct : UiModel()
    }

    init {
        initScope()
    }

    fun cancelJobs() {
        if (::getTransactionsByProductJob.isInitialized && getTransactionsByProductJob.isActive) {
            getTransactionsByProductJob.cancel()
        }
        if (::updateProductDetailsJob.isInitialized && updateProductDetailsJob.isActive) {
            updateProductDetailsJob.cancel()
        }
        if (::getRatesJob.isInitialized && getRatesJob.isActive) {
            getRatesJob.cancel()
        }
    }

    override fun getProductDetailsData(productName: String, chosenCurrency: String) {
        lateinit var productDetails: ProductDetailsModel
        var transactionDetailList: List<TransactionDetailsModel>
        var transactionsData: List<TransactionModel>
        var rateList: List<RateModel>

        getTransactionsByProductJob = CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.IO) {
                rateList = ratesUseCases.getRatesFromLocal()
                if (rateList.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        _model.value =
                            UiModel.NotRateDataFoundLocally
                    }
                } else {
                    val rateChosenCurrency =
                        RatesController.getChosenCurrencyRate(rateList, chosenCurrency)
                    transactionsData = transactionsUseCases.getTransactionsByProduct(productName)
                    transactionDetailList =
                        transactionsData.toTransactionDetailsModelList(rateChosenCurrency)

                    productDetails = ProductDetailsModel(
                        productName,
                        transactionDetailList,
                        transactionDetailList.sumByDouble {
                            it.conversionToChosenCurrency
                        }
                    )

                    if (transactionDetailList.isNullOrEmpty()) {
                        withContext(Dispatchers.Main) {
                            _model.value =
                                UiModel.ErrorGettingTransactionsByProduct
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            _model.value =
                                UiModel.UpdateProductDetails(
                                    productDetails
                                )
                        }
                    }
                }
            }
        }
    }

    override fun updateProductDetailsData(productDetails: ProductDetailsModel) {
        var productUpdated: Boolean

        updateProductDetailsJob = CoroutineScope(Dispatchers.IO).launch {

            productUpdated = productUseCases.updateProductDetails(productDetails)

            if (productUpdated) {
                withContext(Dispatchers.Main) {
                    if (productDetails.transactions.isNullOrEmpty()) {
                        _model.value = UiModel.NotProductTransactionsFoundLocally
                    } else {
                        _model.value = UiModel.ShowProductDetailsData(
                            productDetails
                        )
                    }
                }

            } else {
                withContext(Dispatchers.Main) {
                    _model.value = UiModel.ErrorUpdatingProductDetails
                }
            }
        }
    }

}