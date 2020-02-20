package com.macdonald.angel.gnb.ui.features.productDetails

import com.macdonald.angel.data.model.TransactionModel

interface ProductDetailsContract {
    interface View {
        fun initializeViews()
        fun updateUi(model: ProductDetailsViewModel.UiModel)
        fun canNotGetAnyData()
    }

    interface ViewModel {
        fun getTransactionsByProduct()
        fun updateProductTransactions(transactions: List<TransactionModel>)
    }

}
