package com.macdonald.angel.gnb.ui

interface TransactionsListContract {
    interface View {
        fun initializeViews()
        fun updateUi(model: TransactionsListViewModel.UiModel)
    }

    interface ViewModel {
        suspend fun getAllTransactions()
    }

}
