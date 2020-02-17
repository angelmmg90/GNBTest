package com.amacdong.usertaskcontrol.ui.features.tasksList.allTasks

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amacdong.data.model.TaskModel
import com.amacdong.usercase.TaskUserCase
import com.amacdong.usertaskcontrol.common.ScopedViewModel
import kotlinx.coroutines.*

class ListTasksViewModel (
    private val ctx: Application,
    private val taskUserCase: TaskUserCase
): ScopedViewModel(),
    ListTasksContract.ViewModel {

    private lateinit var tasksData: List<TaskModel>
    private lateinit var getTasksJob: Job

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        class ShowTasks(val listFarms: List<TaskModel>) : UiModel()
        object BdError : UiModel()
    }

    init {
        initScope()
    }

    fun cancelJobs() {
        if (::getTasksJob.isInitialized && getTasksJob.isActive) {
            getTasksJob.cancel()
        }
    }

    override fun viewLoaded() {
        tasksData = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            getAllTasks()
        }
    }

    override suspend fun getAllTasks() {
        withContext(Dispatchers.IO) {
            tasksData = taskUserCase.getAllTasks()
        }
        if (tasksData.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                _model.value =
                    UiModel.BdError
            }
        } else {
            withContext(Dispatchers.Main) {
                _model.value =
                    UiModel.ShowTasks(
                        tasksData
                    )
            }
        }
    }
}