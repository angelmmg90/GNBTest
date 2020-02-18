package com.macdonald.angel.gnb.data.networking.services

import com.macdonald.angel.domain.RateDomain
import com.macdonald.angel.domain.transactionsUseCase.TransactionDomain
import retrofit2.http.*

interface ApiServices {

    @GET("rates")
    @Headers("Accept: application/json")
    suspend fun getRates(): Array<RateDomain>

    @GET("transactions")
    @Headers("Accept: application/json")
    suspend fun getTransactions(): Array<TransactionDomain>

}
