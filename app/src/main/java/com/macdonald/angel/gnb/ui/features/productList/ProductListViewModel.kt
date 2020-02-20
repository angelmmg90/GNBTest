package com.macdonald.angel.gnb.ui.features.productList

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.domain.transactionsUseCase.TransactionDomain
import com.macdonald.angel.gnb.common.ScopedViewModel
import com.macdonald.angel.gnb.data.getProductsFromTransactionsList
import com.macdonald.angel.gnb.data.toTransactionModel
import com.macdonald.angel.usecases.ProductsUseCases
import com.macdonald.angel.usecases.TransactionsUseCases
import kotlinx.coroutines.*

class ProductListViewModel(
    private val ctx: Application,
    private val productUseCases: ProductsUseCases,
    private val transactionsUseCases: TransactionsUseCases
):ScopedViewModel(), ProductListContract.ViewModel {
    private lateinit var getProductsJob: Job
    private lateinit var getTransactionsJob: Job
    private lateinit var insertProductsJob: Job

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        class ShowProducts(val productList: List<ProductModel>) : UiModel()
        class InsertProducts(val productList: List<ProductModel>) : UiModel()

        object Forbbiden : UiModel()
        object NotProductDataFoundLocally : UiModel()
        object NetWorkError : UiModel()
        object ErrorInsertingProducts : UiModel()
        object ErrorGettingTransactions : UiModel()
        object ErrorGettingLocalTransactions : UiModel()
    }

    init {
        initScope()
    }

    fun cancelJobs() {
        if (::getProductsJob.isInitialized && getProductsJob.isActive) {
            getProductsJob.cancel()
        }
        if (::getTransactionsJob.isInitialized && getTransactionsJob.isActive) {
            getTransactionsJob.cancel()
        }

        if (::insertProductsJob.isInitialized && insertProductsJob.isActive) {
            insertProductsJob.cancel()
        }
    }

    override fun getAllProductsFromLocal() {
        lateinit var productData: List<ProductModel>

        getProductsJob = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                productData = productUseCases.getProductsFromLocal()
            }
            if (productData.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    _model.value =
                        UiModel.NotProductDataFoundLocally
                }
            } else {
                withContext(Dispatchers.Main) {
                    _model.value =
                        UiModel.ShowProducts(
                            productData
                        )
                }
            }
        }

    }

    override fun getProductsFromLocalTransactions() {
        lateinit var transactionsData: List<TransactionModel>
        var productList: ArrayList<ProductModel> = ArrayList()

        getTransactionsJob = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                transactionsData = transactionsUseCases.getTransactionsFromLocal()
            }
            if (transactionsData.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    _model.value =
                        UiModel.ErrorGettingLocalTransactions
                }
            } else {
                withContext(Dispatchers.Main) {
                    var productsFromTransactionList =
                        productList.getProductsFromTransactionsList(ArrayList(transactionsData))

                    _model.value =
                        UiModel.InsertProducts(
                            productsFromTransactionList
                        )
                }
            }
        }

    }


    override fun getProductsFromRemoteTransactions() {
        lateinit var response: Response<Array<TransactionDomain>>
        var productList: ArrayList<ProductModel> = ArrayList()

        getTransactionsJob = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                response = transactionsUseCases.getTransactionsFromRemote()
            }
            when (response) {
                is Response.Forbidden -> {
                    withContext(Dispatchers.Main) {
                        _model.value =
                            UiModel.Forbbiden
                    }
                }

                is Response.Error -> {
                    withContext(Dispatchers.Main) {
                        _model.value =
                            UiModel.ErrorGettingTransactions
                    }
                }

                is Response.NetWorkError -> {
                    withContext(Dispatchers.Main) {
                        _model.value =
                            UiModel.NetWorkError
                    }
                }

                is Response.Success -> {
                    var transactionsListModel = ArrayList<TransactionModel>()
                    var rawListTransactions = (response as Response.Success<Array<TransactionDomain>>).data

                    rawListTransactions.forEach {
                        transactionsListModel.add(it.toTransactionModel())
                    }

                    transactionsUseCases.persistTransactionsIntoDatabase(transactionsListModel)

                    withContext(Dispatchers.Main) {
                        var productsFromTransactionList =
                            productList.getProductsFromTransactionsList(transactionsListModel)

                        _model.value =
                            UiModel.InsertProducts(
                                productsFromTransactionList
                            )
                    }
                }
            }
        }

    }

    override fun insertAllProducts(products: List<ProductModel>) {
        var productsInserted: Boolean

        insertProductsJob = CoroutineScope(Dispatchers.IO).launch {
            productsInserted = productUseCases.persistProductsIntoDatabase(products)

            if (productsInserted) {

                withContext(Dispatchers.Main) {
                    _model.value = UiModel.ShowProducts(products)
                }

            } else {
                withContext(Dispatchers.Main) {
                    _model.value = UiModel.ErrorInsertingProducts
                }
            }
        }
    }

}