package com.macdonald.angel.gnb.data.networking.datasources.rates

import android.content.Context
import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.data.sources.RatesRemoteDatasource
import com.macdonald.angel.domain.ratesUseCase.RateDomain

class ConcretionRatesRemoteDatasource(val context: Context): RatesRemoteDatasource {
    override suspend fun getRatesFromRemote(): Response<Array<RateDomain>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

