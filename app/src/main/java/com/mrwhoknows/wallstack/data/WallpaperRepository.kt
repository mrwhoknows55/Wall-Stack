package com.mrwhoknows.wallstack.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mrwhoknows.wallstack.api.WallpaperAPI
import com.mrwhoknows.wallstack.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WallpaperRepository @Inject constructor(private val wallpaperAPI: WallpaperAPI) {
    fun getSearchResult(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { WallpaperPagingSource(wallpaperAPI, query) }
        ).liveData
}