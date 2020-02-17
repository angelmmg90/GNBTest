package com.amacdong.data.sources

import com.amacdong.data.model.FarmModel

interface FarmLocalDatasource {
    suspend fun getFarmsFromLocal(): List<FarmModel>
    suspend fun persistFarmsIntoDatabase(farmsDomain: List<FarmModel>): Boolean
}