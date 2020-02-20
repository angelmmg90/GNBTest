package com.macdonald.angel.usecases

import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.repositories.ProductsRepository
import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.data.repositories.TransactionsRepository
import com.macdonald.angel.domain.transactionsUseCase.TransactionDomain

class ProductsUseCases(
    private val productsRepository: ProductsRepository
) {
    suspend fun getProductsFromLocal() : List<ProductModel> =
        productsRepository.getProductsFromLocal()

    suspend fun persistProductsIntoDatabase(products: List<ProductModel>) : Boolean =
        productsRepository.persistProductsIntoDatabase(products)

    suspend fun updateProduct(product: ProductModel) : Boolean =
        productsRepository.updateProduct(product)

}