package com.macdonald.angel.data.repositories

import com.macdonald.angel.data.sources.RatesRemoteDatasource
import com.macdonald.angel.domain.ratesUseCase.RateDomain

class RatesRepository (
    private val ratesRemoteDatasource: RatesRemoteDatasource
) {
    suspend fun getRatesFromRemote(): Response<Array<RateDomain>> =
        ratesRemoteDatasource.getRatesFromRemote()
}