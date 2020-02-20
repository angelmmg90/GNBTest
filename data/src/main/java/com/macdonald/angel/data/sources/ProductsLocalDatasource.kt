package com.macdonald.angel.data.sources

import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.domain.ratesUseCase.RateDomain

interface ProductsLocalDatasource {
    suspend fun getProductsFromLocal(): List<ProductModel>
    suspend fun persistProductsIntoDatabase(products: List<ProductModel>): Boolean
    suspend fun updateProduct(product: ProductModel): Boolean
}