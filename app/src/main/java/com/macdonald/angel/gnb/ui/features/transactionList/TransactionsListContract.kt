package com.macdonald.angel.gnb.ui.features.transactionList

interface TransactionsListContract {
    interface View {
        fun initializeViews()
        fun updateUi(model: TransactionsListViewModel.UiModel)
    }

    interface ViewModel {
        fun getAllTransactions()
    }

}
