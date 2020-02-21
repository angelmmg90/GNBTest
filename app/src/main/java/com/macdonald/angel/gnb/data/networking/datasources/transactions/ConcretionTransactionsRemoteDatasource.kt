package com.macdonald.angel.gnb.data.networking.datasources.transactions

import android.content.Context
import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.data.sources.TransactionsRemoteDatasource
import com.macdonald.angel.domain.transactionsUseCase.TransactionDomain
import com.macdonald.angel.gnb.data.networking.NetworkObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.HttpException
import kotlin.coroutines.resume

class ConcretionTransactionsRemoteDatasource(val context: Context): TransactionsRemoteDatasource {
    private lateinit var transactions: Response<Array<TransactionDomain>>

    override suspend fun getTransactionsFromRemote(): Response<Array<TransactionDomain>> =
        suspendCancellableCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = NetworkObject.service.getTransactions()

                    if (response != null) {
                        transactions = Response.Success(response)
                        continuation.resume(transactions)
                    } else {
                        continuation.resume(Response.Error(Throwable()))
                    }
                } catch (e: Exception) {
                    transactions = Response.NetWorkError
                    continuation.resume(transactions)
                }
            }
        }
    }

