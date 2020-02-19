package com.macdonald.angel.data.repositories

import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.data.sources.TransactionsLocalDatasource
import com.macdonald.angel.data.sources.TransactionsRemoteDatasource
import com.macdonald.angel.domain.transactionsUseCase.TransactionDomain

class TransactionsRepository (
    private val transactionsRemoteDatasource: TransactionsRemoteDatasource,
    private val transactionsLocalDatasource: TransactionsLocalDatasource

) {
    suspend fun getTransactionsFromRemote(): Response<Array<TransactionDomain>> =
        transactionsRemoteDatasource.getTransactionsFromRemote()

    suspend fun getTransactionsFromLocal(): List<TransactionModel> =
        transactionsLocalDatasource.getTransactionsFromLocal()

    suspend fun persistTransactionsIntoDatabase(transactions: List<TransactionModel>): Boolean =
        transactionsLocalDatasource.persistTransactionsIntoDatabase(transactions)

}