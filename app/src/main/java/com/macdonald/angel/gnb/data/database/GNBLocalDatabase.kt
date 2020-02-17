package com.macdonald.angel.gnb.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.macdonald.angel.gnb.data.database.daos.RateDAO
import com.macdonald.angel.gnb.data.database.daos.TransactionDAO
import com.macdonald.angel.gnb.data.database.entities.RateEntity
import com.macdonald.angel.gnb.data.database.entities.TransactionEntity

@Database(
    entities = [
        RateEntity::class,
        TransactionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GNBLocalDatabase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "gnb-db"

        fun build(context: Context): GNBLocalDatabase = Room.databaseBuilder(
            context,
            GNBLocalDatabase::class.java,
            DATABASE_NAME)
            .build()
    }

    abstract fun rateDAO(): RateDAO
    abstract fun transactionDAO(): TransactionDAO
}