package com.macdonald.angel.gnb.ui.features.productDetails

import com.macdonald.angel.data.model.CurrencyType
import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.model.ProductDetailsModel
import com.macdonald.angel.data.model.RateModel

interface ProductDetailsContract {
    interface View {
        fun initializeViews()
        fun updateUi(model: ProductDetailsViewModel.UiModel)
    }

    interface ViewModel {
        fun getProductDetailsData(productName: String,
                                  chosenCurrency: String = CurrencyType.EUR.currency)
        fun updateProductDetailsData(productDetails: ProductDetailsModel)
    }

}
