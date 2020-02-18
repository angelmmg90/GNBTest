package com.macdonald.angel.data.sources

import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.domain.ratesUseCase.RateDomain

interface RatesRemoteDatasource {
    suspend fun getRatesFromRemote(): Response<Array<RateDomain>>
}