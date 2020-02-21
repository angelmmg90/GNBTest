package com.macdonald.angel.gnb.data.networking

import com.macdonald.angel.gnb.BuildConfig
import com.macdonald.angel.gnb.data.networking.services.ApiServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkObject {
    private const val BASE_URL =  BuildConfig.BASEURL

    val service: ApiServices = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .run {
            create(ApiServices::class.java)
        }
}