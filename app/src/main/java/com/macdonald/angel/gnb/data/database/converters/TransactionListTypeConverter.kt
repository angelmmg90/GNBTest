package com.wfp.pocketbook.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wfp.data.entities.SubchapterEntity

inline fun <reified T> genericType() = object : TypeToken<T>() {}.type

class SubChapterTypeConverter {
    @TypeConverter
    fun stringToSomeObjectList(data: String): List<SubchapterEntity> {
        val listType = genericType<List<SubchapterEntity>>()

        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<SubchapterEntity>): String {
        return Gson().toJson(someObjects)
    }
}