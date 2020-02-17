package com.macdonald.angel.gnb.data.networking.services

import com.macdonald.angel.domain.RateDomain
import com.macdonald.angel.domain.TransactionDomain
import retrofit2.http.*

interface ApiServices {

    @GET("rates.json")
    suspend fun getRates(): Array<RateDomain>

    @GET("transactions.json")
    suspend fun getTransactions(): Array<TransactionDomain>

}
