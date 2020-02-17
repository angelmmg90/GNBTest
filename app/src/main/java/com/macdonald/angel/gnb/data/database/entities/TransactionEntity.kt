package com.macdonald.angel.gnb.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "transaction_entity"
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "transaction_id") val id: Int = 0,
    val product : String = "",
    val amount : Double,
    val currency : String = ""
)