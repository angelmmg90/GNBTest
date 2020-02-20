package com.macdonald.angel.usecases

import com.macdonald.angel.data.model.RateModel
import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.data.repositories.RatesRepository
import com.macdonald.angel.domain.ratesUseCase.RateDomain

class RatesUseCases(
    private val ratesRepository: RatesRepository
) {
    suspend fun getRatesFromRemote() : Response<Array<RateDomain>> =
        ratesRepository.getRatesFromRemote()

    suspend fun getRatesFromLocal() : List<RateModel> =
        ratesRepository.getRatesFromLocal()

    suspend fun persistRatesIntoDatabase(rates: List<RateModel>) : Boolean =
        ratesRepository.persistRatesIntoDatabase(rates)


}