package com.mrwhoknows.wallstack.screens.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mrwhoknows.wallstack.adapter.WallpaperAdapter
import com.mrwhoknows.wallstack.api.RetrofitFactory
import com.mrwhoknows.wallstack.model.Wallpaper
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import retrofit2.HttpException

private const val TAG = "WallpaperListViewModel"

class WallpaperListViewModel : ViewModel() {

    private var _dataList: MutableLiveData<List<Wallpaper.Data>> = MutableLiveData()
    val dataList = _dataList

    var isSuccessful = true
    private val service = RetrofitFactory.makeRetrofitService()

    init {
        Log.i(TAG, "init: called")

    }

    fun getWall() {
        CoroutineScope(IO).launch {
            val response = service.getWall()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        Log.i(TAG, "getWallpapers: success")
                        _dataList.value = response.body()!!.data
                        if (_dataList.value?.size!! > 0) isSuccessful = true
                    } else {
                        Log.e(TAG, "getWallpapers: Error")
                        isSuccessful = false
                    }
                } catch (e: HttpException) {
                    Log.e(TAG, "getWallpapers Exception ${e.message}")
                    isSuccessful = false
                } catch (e: Throwable) {
                    Log.e(TAG, "getWallpapers throwable " + e.localizedMessage!!)
                    isSuccessful = false
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: called")
    }
}