package com.macdonald.angel.usecases

import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.data.repositories.RatesRepository
import com.macdonald.angel.domain.ratesUseCase.RateDomain

class RatesUseCases(
    private val ratesRepository: RatesRepository
) {
    suspend fun getRatesFromRemote() : Response<Array<RateDomain>> =
        ratesRepository.getRatesFromRemote()
}