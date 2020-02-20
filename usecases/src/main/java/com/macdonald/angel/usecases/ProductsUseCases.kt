package com.macdonald.angel.usecases

import com.macdonald.angel.data.model.ProductDetailsModel
import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.repositories.ProductsRepository

class ProductsUseCases(
    private val productsRepository: ProductsRepository
) {
    suspend fun getProductsDetailFromLocal() : List<ProductDetailsModel> =
        productsRepository.getProductsDetailFromLocal()

    suspend fun getProductsModelFromLocal() : List<ProductModel> =
        productsRepository.getProductsModelFromLocal()


    suspend fun persistProductsIntoDatabase(products: List<ProductDetailsModel>) : Boolean =
        productsRepository.persistProductsIntoDatabase(products)

    suspend fun updateProductDetails(productDetails: ProductDetailsModel) : Boolean =
        productsRepository.updateProductDetails(productDetails)

}