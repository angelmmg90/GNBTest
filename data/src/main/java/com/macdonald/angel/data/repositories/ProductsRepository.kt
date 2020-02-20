package com.macdonald.angel.data.repositories

import com.macdonald.angel.data.model.ProductDetailsModel
import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.sources.ProductsLocalDatasource

class ProductsRepository (
    private val productsLocalDatasource: ProductsLocalDatasource

) {
    suspend fun getProductsDetailFromLocal(): List<ProductDetailsModel> =
        productsLocalDatasource.getProductDetailsFromLocal()

    suspend fun getProductsModelFromLocal(): List<ProductModel> =
        productsLocalDatasource.getProductModelFromLocal()


    suspend fun persistProductsIntoDatabase(products: List<ProductDetailsModel>): Boolean =
        productsLocalDatasource.persistProductsIntoDatabase(products)

    suspend fun updateProductDetails(productDetails: ProductDetailsModel): Boolean =
        productsLocalDatasource.updateProductDetails(productDetails)


}