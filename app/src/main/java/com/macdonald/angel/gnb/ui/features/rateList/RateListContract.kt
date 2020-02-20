package com.macdonald.angel.gnb.ui.features.rateList

interface RateListContract {
    interface View {
        fun initializeViews()
        fun updateUi(model: RateListViewModel.UiModel)
        fun canNotGetAnyData()
    }

    interface ViewModel {
        fun getAllRatesFromRemote()
        fun getAllRatesFromLocal()
    }

}
