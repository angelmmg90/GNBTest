package com.macdonald.angel.gnb.ui.features.transactionList

interface TransactionsListContract {
    interface View {
        fun initializeViews()
        fun updateUi(model: TransactionsListViewModel.UiModel)
        fun goToConversionRateInfo()
        fun canNotGetAnyTransaction()
    }

    interface ViewModel {
        fun getAllTransactionsFromRemote()
        fun getAllTransactionsFromLocal()
    }

}
