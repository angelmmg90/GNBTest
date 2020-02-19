package com.macdonald.angel.gnb.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.macdonald.angel.gnb.data.database.entities.TransactionEntity

inline fun <reified T> genericType() = object : TypeToken<T>() {}.type

class TransactionListTypeConverter {
    @TypeConverter
    fun stringToSomeObjectList(data: String): List<TransactionEntity> {
        val listType = genericType<List<TransactionEntity>>()

        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<TransactionEntity>): String {
        return Gson().toJson(someObjects)
    }
}