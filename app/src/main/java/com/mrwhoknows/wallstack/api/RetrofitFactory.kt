package com.mrwhoknows.wallstack.api

import com.mrwhoknows.wallstack.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun makeRetrofitService(): WallpaperAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WallpaperAPI::class.java)
    }
}