package com.macdonald.angel.data.sources

import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.domain.transactionsUseCase.TransactionDomain

interface TransactionsLocalDatasource {
    suspend fun getTransactionsFromLocal(): List<TransactionModel>
    suspend fun persistTransactionsIntoDatabase(transactions: List<TransactionModel>): Boolean
    suspend fun getTransactionsByProduct(productName: String): List<TransactionModel>
}