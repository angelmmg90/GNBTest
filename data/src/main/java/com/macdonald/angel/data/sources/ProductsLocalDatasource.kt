package com.macdonald.angel.data.sources

import com.macdonald.angel.data.model.ProductDetailsModel
import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.domain.ratesUseCase.RateDomain

interface ProductsLocalDatasource {
    suspend fun getProductDetailsFromLocal(): List<ProductDetailsModel>
    suspend fun getProductModelFromLocal(): List<ProductModel>
    suspend fun persistProductsIntoDatabase(products: List<ProductDetailsModel>): Boolean
    suspend fun updateProductDetails(productDetails: ProductDetailsModel): Boolean
}