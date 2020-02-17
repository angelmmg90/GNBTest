package com.macdonald.angel.data.repositories

import com.macdonald.angel.data.sources.TransactionsRemoteDatasource
import com.macdonald.angel.domain.transactionsUseCase.TransactionDomain

class TransactionsRepository (
    private val transactionsRemoteDatasource: TransactionsRemoteDatasource
) {
    suspend fun getTransactionsFromRemote(): Response<Array<TransactionDomain>> =
        transactionsRemoteDatasource.getTransactionsFromRemote()
}