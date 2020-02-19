package com.macdonald.angel.usecases

import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.data.repositories.TransactionsRepository
import com.macdonald.angel.domain.transactionsUseCase.TransactionDomain

class TransactionsUseCases(
    private val transactionsRepository: TransactionsRepository
) {
    suspend fun getTransactionsFromRemote() : Response<Array<TransactionDomain>> =
        transactionsRepository.getTransactionsFromRemote()

    suspend fun getTransactionsFromLocal() : List<TransactionModel> =
        transactionsRepository.getTransactionsFromLocal()

    suspend fun persistTransactionsIntoDatabase(transactions: List<TransactionModel>) : Boolean =
        transactionsRepository.persistTransactionsIntoDatabase(transactions)

}