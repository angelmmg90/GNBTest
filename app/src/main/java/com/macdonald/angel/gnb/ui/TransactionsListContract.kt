package com.amacdong.usertaskcontrol.ui.features.tasksList.allTasks

interface ListTasksContract {
    interface View {
        fun initializeViews()
        fun createNewTask()
        fun updateUi(model: ListTasksViewModel.UiModel)
    }

    interface ViewModel {
        fun viewLoaded()
        suspend fun getAllTasks()
    }

}
