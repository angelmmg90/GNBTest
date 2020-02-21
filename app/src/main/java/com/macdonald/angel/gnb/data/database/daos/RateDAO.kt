package com.macdonald.angel.gnb.data.database.daos

import androidx.room.*
import com.macdonald.angel.gnb.data.database.entities.RateEntity

@Dao
interface RateDAO {

    @Transaction
    suspend fun insertIncomingRates(incomingRates: List<RateEntity>){
        deleteAllRates()
        insertRates(incomingRates)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRate(rate: RateEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRates(rates: List<RateEntity>)

    @Delete
    fun deleteRate(rate: RateEntity)

    @Query("DELETE FROM rate_entity")
    fun deleteAllRates()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRate(rate: RateEntity)

    @Query("SELECT * from rate_entity")
    fun getRates(): List<RateEntity>

}