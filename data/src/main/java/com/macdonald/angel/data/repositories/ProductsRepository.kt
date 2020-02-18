package com.macdonald.angel.data.repositories

import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.sources.ProductsLocalDatasource

class ProductsRepository (
    private val productsLocalDatasource: ProductsLocalDatasource

) {
    suspend fun getProductsFromLocal(): List<ProductModel> =
        productsLocalDatasource.getProductsFromLocal()

    suspend fun persistProductsIntoDatabase(products: List<ProductModel>): Boolean =
        productsLocalDatasource.persistProductsIntoDatabase(products)
}