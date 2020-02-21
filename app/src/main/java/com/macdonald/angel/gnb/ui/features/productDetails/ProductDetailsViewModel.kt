package com.macdonald.angel.gnb.ui.features.productDetails

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.macdonald.angel.data.model.ProductDetailsModel
import com.macdonald.angel.data.model.TransactionDetailsModel
import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.gnb.common.ScopedViewModel
import com.macdonald.angel.gnb.data.toTransactionDetailsModelList
import com.macdonald.angel.usecases.ProductsUseCases
import com.macdonald.angel.usecases.TransactionsUseCases
import kotlinx.coroutines.*

class ProductDetailsViewModel(
    private val ctx: Application,
    private val productUseCases: ProductsUseCases,
    private val transactionsUseCases: TransactionsUseCases
):ScopedViewModel(), ProductDetailsContract.ViewModel {


    private lateinit var getTransactionsByProductJob: Job
    private lateinit var updateProductDetailsJob: Job

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        class ShowProductDetailsData(val productDetails: ProductDetailsModel) : UiModel()
        class UpdateProductDetails(val productDetails: ProductDetailsModel) : UiModel()

        object NotProductTransactionsFoundLocally : UiModel()
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
    }

    override fun getProductDetailsData(productName: String) {
        lateinit var transactionsData: List<TransactionModel>
        lateinit var productDetails: ProductDetailsModel
        lateinit var transactionDetailList: List<TransactionDetailsModel>

        getTransactionsByProductJob = CoroutineScope(Dispatchers.IO).launch {

            //TODO meter la lógica de conversión de monedas
            withContext(Dispatchers.IO) {
                transactionsData = transactionsUseCases.getTransactionsByProduct(productName)
                transactionDetailList = transactionsData.toTransactionDetailsModelList()

                productDetails = ProductDetailsModel(
                    productName,
                    transactionsData.toTransactionDetailsModelList()
                )//TODO falta poner la suma total de las transacciones
            }
            if (transactionsData.isNullOrEmpty()) {
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

    override fun updateProductDetailsData(productDetails: ProductDetailsModel) {
        var productUpdated: Boolean

        updateProductDetailsJob = CoroutineScope(Dispatchers.IO).launch {

            productUpdated = productUseCases.updateProductDetails(productDetails)

            if (productUpdated) {

                withContext(Dispatchers.Main) {
                    if(productDetails.transactions.isNullOrEmpty()){
                        _model.value = UiModel.NotProductTransactionsFoundLocally
                    }else{
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

    private fun getEURAmount(){

    }

}