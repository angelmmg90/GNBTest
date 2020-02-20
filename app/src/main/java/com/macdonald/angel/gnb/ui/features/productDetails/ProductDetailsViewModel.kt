package com.macdonald.angel.gnb.ui.features.productDetails

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

class ProductDetailsViewModel(
    private val ctx: Application,
    private val productUseCases: ProductsUseCases
):ScopedViewModel(), ProductDetailsContract.ViewModel {


    private lateinit var getTransactionsByProductJob: Job

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        class ShowTransactionsByProducts(val transactions: List<TransactionModel>) : UiModel()
        class updateProductTransactions(val transactions: List<TransactionModel>) : UiModel()

        object ErrorUpdatingProductTransactions : UiModel()
        object ErrorGettingTransactionsByProduct : UiModel()
        object NotTransactionByProductFound : UiModel()
    }

    init {
        initScope()
    }

    fun cancelJobs() {
        if (::getTransactionsByProductJob.isInitialized && getTransactionsByProductJob.isActive) {
            getTransactionsByProductJob.cancel()
        }
    }

    override fun getTransactionsByProduct() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateProductTransactions(transactions: List<TransactionModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}