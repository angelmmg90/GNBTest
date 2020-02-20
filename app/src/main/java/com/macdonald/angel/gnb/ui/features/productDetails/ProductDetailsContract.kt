package com.macdonald.angel.gnb.ui.features.productDetails

import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.model.ProductDetailsModel

interface ProductDetailsContract {
    interface View {
        fun initializeViews()
        fun updateUi(model: ProductDetailsViewModel.UiModel)
        fun canNotGetAnyData()
    }

    interface ViewModel {
        fun getTransactionsByProductName(productName: String)
        fun updateProductDetails(productDetails: ProductDetailsModel)
    }

}
