package com.macdonald.angel.data.sources

import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.domain.transactionsUseCase.TransactionDomain

interface TransactionsRemoteDatasource {
    suspend fun getTransactionsFromRemote(): Response<Array<TransactionDomain>>
}