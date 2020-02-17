package com.amacdong.data.repositories

import com.amacdong.data.model.FarmModel
import com.amacdong.data.sources.FarmLocalDatasource
import com.amacdong.data.sources.FarmRemoteDatasource
import com.amacdong.domain.farmUserCase.FarmDomain

class FarmRepository (
    private val farmRemoteDatasource: FarmRemoteDatasource,
    private val farmLocalDatasource: FarmLocalDatasource
) {
    suspend fun getFarmsFromRemote(): Response<Array<FarmDomain>> =
        farmRemoteDatasource.getFarmsFromRemote()

    suspend fun getFarmsFromLocal(): List<FarmModel> =
        farmLocalDatasource.getFarmsFromLocal()

    suspend fun persistFarmsIntoDatabase(farmsDomain: List<FarmModel>) =
        farmLocalDatasource.persistFarmsIntoDatabase(farmsDomain)
}