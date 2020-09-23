package com.mrwhoknows.wallstack.data

import com.mrwhoknows.wallstack.api.WallpaperAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WallpaperRepository @Inject constructor(private val wallpaperAPI: WallpaperAPI) {

}