package com.macdonald.angel.data.sources

import com.macdonald.angel.data.model.RateModel

interface RatesLocalDatasource {
    suspend fun getRatesFromLocal(): List<RateModel>
    suspend fun persistRatesIntoDatabase(rates: List<RateModel>): Boolean
}