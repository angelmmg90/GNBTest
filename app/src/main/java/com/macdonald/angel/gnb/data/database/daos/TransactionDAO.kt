package com.macdonald.angel.gnb.data.database.daos

import androidx.room.*
import com.macdonald.angel.gnb.data.database.entities.TransactionEntity

@Dao
interface TransactionDAO {

    @Transaction
    suspend fun insertIncomingTransactions(incomingTransactions: List<TransactionEntity>){
        deleteAllTransactions()
        inserTransactions(incomingTransactions)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTransaction(rate: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserTransactions(transactions: List<TransactionEntity>)

    @Delete
    fun deleteTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transaction_entity")
    fun deleteAllTransactions()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTransaction(rate: TransactionEntity)

    @Query("SELECT * from transaction_entity")
    fun getTransactions(): List<TransactionEntity>

}