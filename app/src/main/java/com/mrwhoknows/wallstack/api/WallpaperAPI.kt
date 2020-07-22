package com.mrwhoknows.wallstack.api

import com.mrwhoknows.wallstack.model.Wallpaper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpaperAPI {

    @GET("api/v1/search?sorting=random")
    suspend fun getWall(
        @Query("q")
        query: String = "-Anime",
        @Query("resolutions")
        resolution: String = "1080x1920,2160x3840"
    ): Response<Wallpaper>
}