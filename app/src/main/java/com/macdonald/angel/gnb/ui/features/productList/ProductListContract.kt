package com.macdonald.angel.gnb.ui.features.productList

interface ProductListContract {
    interface View {
        fun initializeViews()
        fun updateUi(model: ProductListViewModel.UiModel)
    }

    interface ViewModel {
        fun getAllProducts()
        fun insertAllProducts()
    }

}
