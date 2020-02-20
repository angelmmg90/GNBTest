package com.macdonald.angel.gnb.data.networking.datasources.rates

import com.macdonald.angel.data.model.RateModel
import com.macdonald.angel.data.sources.RatesLocalDatasource
import com.macdonald.angel.gnb.data.database.GNBLocalDatabase
import com.macdonald.angel.gnb.data.database.entities.RateEntity
import com.macdonald.angel.gnb.data.toRateEntity
import com.macdonald.angel.gnb.data.toRateModel

class ConcretionRateLocalDatasource(private var db: GNBLocalDatabase) : RatesLocalDatasource {
    override suspend fun getRatesFromLocal(): List<RateModel> {
        val listRatesModel = ArrayList<RateModel>()

        db.rateDAO().getRates().forEach {
            listRatesModel.add(it.toRateModel())
        }

        return listRatesModel
    }

    override suspend fun persistRatesIntoDatabase(rates: List<RateModel>): Boolean {
        val ratesEntityList = ArrayList<RateEntity>()

        return try {
            rates.forEach {
                ratesEntityList.add(it.toRateEntity())
            }

            db.rateDAO().insertIncomingRates(ratesEntityList)
            return true
        } catch (e: java.lang.Exception) {
            false
        }
    }
}

