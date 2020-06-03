package com.karan.findout.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.karan.findout.data.model.PhotoX

class DbTypeConverters {

    companion object {
        private val gson by lazy { Gson() }
    }

    @TypeConverter
    fun listToString(list: List<String>?): String? {
        return list?.joinToString(", ")
    }

    @TypeConverter
    fun stringToList(string: String?): List<String>? {
        return string?.split(", ")
    }

    @TypeConverter
    fun photoListToJson(list: List<PhotoX>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun jsonToPhotoList(json: String?): List<PhotoX>? {
        return gson.fromJson(json, object : TypeToken<List<PhotoX>>() {}.type)
    }
}
