package com.macdonald.angel.gnb.ui.features.transactionList

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.domain.transactionsUseCase.TransactionDomain
import com.macdonald.angel.gnb.common.ScopedViewModel
import com.macdonald.angel.gnb.data.toTransactionModel
import com.macdonald.angel.usecases.TransactionsUseCases
import kotlinx.coroutines.*

class TransactionsListViewModel(
    private val ctx: Application,
    private val transactionsUserCase: TransactionsUseCases
):ScopedViewModel(), TransactionsListContract.ViewModel {

    private lateinit var getTransactionsJob: Job

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        class ShowTransactions(val transactionList: List<TransactionModel>) : UiModel()
        object Forbbiden : UiModel()
        object ErrorGettingTrasactions : UiModel()
        object NetWorkError : UiModel()
    }

    init {
        initScope()
    }

    fun cancelJobs() {
        if (::getTransactionsJob.isInitialized && getTransactionsJob.isActive) {
            getTransactionsJob.cancel()
        }
    }

    override fun getAllTransactionsFromLocal() {
        lateinit var transactionData: List<TransactionModel>

        getTransactionsJob = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                transactionData = transactionsUserCase.getT()
            }
            if (transactionData.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    _model.value =
                        UiModel.NotProductDataFoundLocally
                }
            } else {
                withContext(Dispatchers.Main) {
                    _model.value =
                        UiModel.ShowProducts(
                            transactionData
                        )
                }
            }
        }
    }

    override fun getAllTransactionsFromRemote() {
        lateinit var response: Response<Array<TransactionDomain>>

        getTransactionsJob = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                response = transactionsUserCase.getTransactionsFromRemote()
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
                            UiModel.ErrorGettingTrasactions
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

                    withContext(Dispatchers.Main) {
                        _model.value =
                            UiModel.ShowTransactions(
                                transactionsListModel
                            )
                    }
                }
            }
        }
    }
}