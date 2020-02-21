package com.macdonald.angel.gnb.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "rate_entity"
)
data class RateEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rate_id") val id: Int = 0,
    val from : String = "",
    val to : String = "",
    val rate : Double,
    val rateChosenCurrency : Double
)