package com.mrwhoknows.wallstack.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mrwhoknows.wallstack.data.WallpaperRepository
import kotlinx.coroutines.coroutineScope

private const val TAG = "WallGalleryViewModel"

class WallGalleryViewModel @ViewModelInject constructor(
    private val repository: WallpaperRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)


    val wallpapers = currentQuery.switchMap { queryString ->
        repository.getSearchResult(queryString).cachedIn(viewModelScope)
    }

    fun searWallpapers(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "wallpapers"
    }
}