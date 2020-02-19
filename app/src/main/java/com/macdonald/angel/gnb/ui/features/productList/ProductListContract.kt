package com.macdonald.angel.gnb.ui.features.productList

import com.macdonald.angel.data.model.ProductModel

interface ProductListContract {
    interface View {
        fun initializeViews()
        fun updateUi(model: ProductListViewModel.UiModel)
    }

    interface ViewModel {
        fun getAllProductsFromLocal()
        fun getProductsFromLocalTransactions()
        fun getProductsFromRemoteTransactions()
        fun insertAllProducts(products: List<ProductModel>)
    }

}
