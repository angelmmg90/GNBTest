package com.macdonald.angel.gnb.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.macdonald.angel.gnb.data.database.converters.TransactionListTypeConverter
import com.macdonald.angel.gnb.data.database.daos.ProductDAO
import com.macdonald.angel.gnb.data.database.daos.RateDAO
import com.macdonald.angel.gnb.data.database.daos.TransactionDAO
import com.macdonald.angel.gnb.data.database.entities.ProductEntity
import com.macdonald.angel.gnb.data.database.entities.RateEntity
import com.macdonald.angel.gnb.data.database.entities.TransactionEntity

@Database(
    entities = [
        RateEntity::class,
        TransactionEntity::class,
        ProductEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(TransactionListTypeConverter::class)
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
    abstract fun productDAO(): ProductDAO
}