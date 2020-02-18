package com.macdonald.angel.gnb.ui.features.rateList

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.macdonald.angel.data.model.RateModel
import com.macdonald.angel.gnb.common.ScopedViewModel
import com.macdonald.angel.usecases.RatesUseCases
import kotlinx.coroutines.Job

class RateListViewModel(
    private val ctx: Application,
    private val ratesUserCase: RatesUseCases
):ScopedViewModel(), RateListContract.ViewModel {

    private lateinit var getRatesJob: Job

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        class ShowRates(val rateList: List<RateModel>) : UiModel()
        object Forbbiden : UiModel()
        object ErrorGettingRates : UiModel()
        object NetWorkError : UiModel()
    }

    init {
        initScope()
    }

    fun cancelJobs() {
        if (::getRatesJob.isInitialized && getRatesJob.isActive) {
            getRatesJob.cancel()
        }
    }

    override fun getAllRates() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}