package com.macdonald.angel.data.repositories

import com.macdonald.angel.data.model.ProductDetailsModel
import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.sources.ProductsLocalDatasource

class ProductsRepository (
    private val productsLocalDatasource: ProductsLocalDatasource

) {
    suspend fun getProductsFromLocal(): List<ProductDetailsModel> =
        productsLocalDatasource.getProductsFromLocal()

    suspend fun getProductsNamesFromLocal(): List<ProductModel> =
        productsLocalDatasource.getProductsNamesFromLocal()


    suspend fun persistProductsIntoDatabase(products: List<ProductDetailsModel>): Boolean =
        productsLocalDatasource.persistProductsIntoDatabase(products)

    suspend fun updateProductDetails(productDetails: ProductDetailsModel): Boolean =
        productsLocalDatasource.updateProductDetails(productDetails)


}