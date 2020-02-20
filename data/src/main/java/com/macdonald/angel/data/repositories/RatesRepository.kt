package com.macdonald.angel.data.repositories

import com.macdonald.angel.data.model.RateModel
import com.macdonald.angel.data.sources.RatesLocalDatasource
import com.macdonald.angel.data.sources.RatesRemoteDatasource
import com.macdonald.angel.domain.ratesUseCase.RateDomain

class RatesRepository (
    private val ratesRemoteDatasource: RatesRemoteDatasource,
    private val ratesLocalDatasource: RatesLocalDatasource

) {
    suspend fun getRatesFromRemote(): Response<Array<RateDomain>> =
        ratesRemoteDatasource.getRatesFromRemote()

    suspend fun getRatesFromLocal(): List<RateModel> =
        ratesLocalDatasource.getRatesFromLocal()

    suspend fun persistRatesIntoDatabase(rates: List<RateModel>): Boolean =
        ratesLocalDatasource.persistRatesIntoDatabase(rates)

}