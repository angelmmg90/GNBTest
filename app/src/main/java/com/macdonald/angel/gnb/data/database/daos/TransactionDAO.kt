package com.macdonald.angel.gnb.data.database.daos

import androidx.room.*
import com.macdonald.angel.gnb.data.database.entities.TransactionEntity

@Dao
interface TransactionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTransaction(rate: TransactionEntity)

    @Delete
    fun deleteTransaction(rate: TransactionEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTransaction(rate: TransactionEntity)

    @Query("SELECT * from transaction_entity")
    fun getRates(): List<TransactionEntity>

}