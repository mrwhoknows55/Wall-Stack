package com.mrwhoknows.wallstack.data

import androidx.paging.PagingSource
import com.mrwhoknows.wallstack.api.WallpaperAPI
import com.mrwhoknows.wallstack.model.Wallpaper
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

private const val WALLPAPER_STARTING_PAGE_INDEX = 1

class WallpaperPagingSource(
    private val wallpaperAPI: WallpaperAPI,
    private val query: String
) : PagingSource<Int, Wallpaper.Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Wallpaper.Data> {
        val pos = params.key ?: WALLPAPER_STARTING_PAGE_INDEX

//        TODO: find out these from model class & structure it like
        return try {
            val response = wallpaperAPI.getWallpaper(query, pos)
            val walls = response.body()
            val data = walls?.data

            LoadResult.Page(
                data = data!!,
                prevKey = if (pos == WALLPAPER_STARTING_PAGE_INDEX) null else pos - 1,
                nextKey = if (data.isEmpty()) null else pos + 1
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}