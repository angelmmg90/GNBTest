package com.macdonald.angel.gnb.data.networking.datasources.rates

import android.content.Context
import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.data.sources.RatesRemoteDatasource
import com.macdonald.angel.domain.ratesUseCase.RateDomain
import com.macdonald.angel.gnb.data.networking.NetworkObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class ConcretionRatesRemoteDatasource(val context: Context): RatesRemoteDatasource {
    private lateinit var rates: Response<Array<RateDomain>>

    override suspend fun getRatesFromRemote(): Response<Array<RateDomain>> =
        suspendCancellableCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = NetworkObject.service.getRates()

                    if (response != null) {
                        rates = Response.Success(response)
                        continuation.resume(rates)
                    } else {
                        continuation.resume(Response.Error(Throwable()))
                    }
                } catch (e: Exception) {
                    rates = Response.NetWorkError
                    continuation.resume(rates)
                }
            }
        }

}

