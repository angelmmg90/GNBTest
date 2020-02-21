package com.macdonald.angel.gnb.ui.features.rateList

import android.util.Log
import com.macdonald.angel.data.model.CurrencyType
import com.macdonald.angel.data.model.RateModel
import com.macdonald.angel.domain.ratesUseCase.RateDomain
import com.macdonald.angel.gnb.data.toRateModel

class RatesController {

    companion object {

        private val EUR: String = CurrencyType.EUR.currency

        fun getCustomRates(rateList: List<RateDomain>): List<RateModel> {
            var ratesToReturn = getDirectConversionToChosenCurrency(rateList)
            var ratesWithoutChosenCurrency = getListWithoutChosenCurrency(rateList)

            do {
                ratesWithoutChosenCurrency.forEach {
                    try {

                        var matchingRate = ratesToReturn.single { rate ->
                            rate.from == it.to && rate.to != it.from
                        }
                        var rateChosenCurrency = it.rate * matchingRate.rateChosenCurrency
                        ratesToReturn.add(it.toRateModel(rateChosenCurrency))

                    } catch (e: Exception) {
                        Log.d("ERROR_GET_CUSTOM_RATES", "Not allowed")
                    }
                }
                var completeRateList = ratesToReturn.size != (rateList.size / 2)
            } while (completeRateList)

            return ratesToReturn
        }


        private fun getDirectConversionToChosenCurrency(rateList: List<RateDomain>) =
            rateList.filter {
                it.to == EUR
            }.map { it.toRateModel(it.rate) }.toMutableList()

        private fun getListWithoutChosenCurrency(rateList: List<RateDomain>) =
            rateList.filter {
                it.to != EUR && it.from != EUR
            }.toMutableList()

    }
}