package com.macdonald.angel.gnb.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "product_entity"
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "product_id") val id: Int = 0,
    val name : String = "",
    val transactions : List<TransactionEntity>?,
    val totalSum : Double
)