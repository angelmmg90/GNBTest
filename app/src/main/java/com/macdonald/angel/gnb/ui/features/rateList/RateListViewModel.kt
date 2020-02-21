package com.macdonald.angel.gnb.ui.features.rateList

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.macdonald.angel.data.model.RateModel
import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.domain.ratesUseCase.RateDomain
import com.macdonald.angel.gnb.common.ScopedViewModel
import com.macdonald.angel.usecases.RatesUseCases
import kotlinx.coroutines.*

class RateListViewModel(
    private val ctx: Application,
    private val ratesUserCase: RatesUseCases
) : ScopedViewModel(), RateListContract.ViewModel {

    private lateinit var getRatesJob: Job

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        class ShowRates(val rateList: List<RateModel>) : UiModel()
        object ErrorGettingRates : UiModel()
        object NetWorkError : UiModel()
        object NotRateDataFoundLocally : UiModel()
    }

    init {
        initScope()
    }

    fun cancelJobs() {
        if (::getRatesJob.isInitialized && getRatesJob.isActive) {
            getRatesJob.cancel()
        }
    }

    override fun getAllRatesFromLocal() {
        lateinit var rateList: List<RateModel>

        getRatesJob = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                rateList = ratesUserCase.getRatesFromLocal()
            }
            if (rateList.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    _model.value =
                        UiModel.NotRateDataFoundLocally
                }
            } else {
                withContext(Dispatchers.Main) {
                    _model.value =
                        UiModel.ShowRates(
                            rateList
                        )
                }
            }
        }
    }

    override fun getAllRatesFromRemote() {
        lateinit var response: Response<Array<RateDomain>>

        getRatesJob = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                response = ratesUserCase.getRatesFromRemote()
            }
            when (response) {
                is Response.Error -> {
                    withContext(Dispatchers.Main) {
                        _model.value =
                            UiModel.ErrorGettingRates
                    }
                }

                is Response.NetWorkError -> {
                    withContext(Dispatchers.Main) {
                        _model.value =
                            UiModel.NetWorkError
                    }
                }

                is Response.Success -> {
                    val ratesListModel: List<RateModel>
                    val rawListRates = (response as Response.Success<Array<RateDomain>>).data

                    ratesListModel = RatesController.getCustomRates(rawListRates.toList())
                    ratesUserCase.persistRatesIntoDatabase(ratesListModel)

                    withContext(Dispatchers.Main) {
                        _model.value =
                            UiModel.ShowRates(
                                ratesListModel
                            )
                    }
                }
            }
        }
    }
}