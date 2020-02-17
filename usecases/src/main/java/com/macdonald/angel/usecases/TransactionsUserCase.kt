package com.amacdong.usercase

import com.amacdong.data.model.FarmModel
import com.amacdong.data.repositories.FarmRepository
import com.amacdong.data.repositories.Response
import com.amacdong.domain.farmUserCase.FarmDomain

class FarmUserCase(private val farmRepository: FarmRepository) {
    suspend fun getFarmsFromRemote() : Response<Array<FarmDomain>> =
        farmRepository.getFarmsFromRemote()

    suspend fun getFarmsFromLocal(): List<FarmModel> =
        farmRepository.getFarmsFromLocal()

    suspend fun persistFarmsIntoDatabase(farmsDomain: List<FarmModel>) =
        farmRepository.persistFarmsIntoDatabase(farmsDomain)
}