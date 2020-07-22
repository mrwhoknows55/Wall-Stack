package com.mrwhoknows.wallstack.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    const val BASE_URL = "https://wallhaven.cc/"

    fun makeRetrofitService(): WallpaperAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WallpaperAPI::class.java)
    }
}