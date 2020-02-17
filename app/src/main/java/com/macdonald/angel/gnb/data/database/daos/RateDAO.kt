package com.macdonald.angel.gnb.data.database.daos

import androidx.room.*
import com.macdonald.angel.gnb.data.database.entities.RateEntity

@Dao
interface RateDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRate(rate: RateEntity)

    @Delete
    fun deleteRate(rate: RateEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRate(rate: RateEntity)

    @Query("SELECT * from rate_entity")
    fun getRates(): List<RateEntity>

}