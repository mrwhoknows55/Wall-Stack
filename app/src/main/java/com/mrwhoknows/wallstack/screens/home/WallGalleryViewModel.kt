package com.mrwhoknows.wallstack.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.mrwhoknows.wallstack.data.WallpaperRepository

private const val TAG = "WallGalleryViewModel"

class WallGalleryViewModel @ViewModelInject constructor(
    private val repository: WallpaperRepository
) : ViewModel() {

}