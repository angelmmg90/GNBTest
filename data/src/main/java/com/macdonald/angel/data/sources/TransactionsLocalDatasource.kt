package com.macdonald.angel.data.sources

import com.macdonald.angel.data.model.TransactionModel

interface TransactionsLocalDatasource {
    suspend fun getTransactionsFromLocal(): List<TransactionModel>
    suspend fun persistTransactionsIntoDatabase(transactions: List<TransactionModel>): Boolean
    suspend fun getTransactionsByProduct(productName: String): List<TransactionModel>
}