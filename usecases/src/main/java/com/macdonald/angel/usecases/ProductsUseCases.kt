package com.macdonald.angel.usecases

import com.macdonald.angel.data.model.ProductDetailsModel
import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.repositories.ProductsRepository

class ProductsUseCases(
    private val productsRepository: ProductsRepository
) {
    suspend fun getProductsFromLocal() : List<ProductDetailsModel> =
        productsRepository.getProductsFromLocal()

    suspend fun getProductsNamesFromLocal() : List<ProductModel> =
        productsRepository.getProductsNamesFromLocal()


    suspend fun persistProductsIntoDatabase(products: List<ProductDetailsModel>) : Boolean =
        productsRepository.persistProductsIntoDatabase(products)

    suspend fun updateProductDetails(productDetails: ProductDetailsModel) : Boolean =
        productsRepository.updateProductDetails(productDetails)

}