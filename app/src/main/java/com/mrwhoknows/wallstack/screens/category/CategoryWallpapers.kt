package com.mrwhoknows.wallstack.screens.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mrwhoknows.wallstack.R
import com.mrwhoknows.wallstack.adapter.WallpaperAdapter
import com.mrwhoknows.wallstack.api.RetrofitFactory
import com.mrwhoknows.wallstack.model.Wallpaper
import kotlinx.android.synthetic.main.fragment_wallpaper_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

private const val TAG = "CategoryWallpapers"

class CategoryWallpapers : Fragment(), WallpaperAdapter.WallpaperListener {

    private lateinit var wallpaperAdapter: WallpaperAdapter
    private lateinit var wallpapers: List<Wallpaper.Data>

    private lateinit var args: CategoryWallpapersArgs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        args = CategoryWallpapersArgs.fromBundle(requireArguments())

        return inflater.inflate(R.layout.fragment_wallpapers_in_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wallpapers = listOf()

        getWall()
    }

    private fun initRecyclerView(dataList: List<Wallpaper.Data>) {
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        wallpaperAdapter = WallpaperAdapter(dataList, this)
        recyclerView.adapter = wallpaperAdapter
    }

    private fun getWall() {
        val service = RetrofitFactory.makeRetrofitService()

        CoroutineScope(Dispatchers.IO).launch {

            val response = service.getWall(args.categoryName, "")

            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        Log.i(TAG, "getWallpapers: success")
                        wallpapers = response.body()!!.data
                        initRecyclerView(wallpapers)
                    } else {
                        Log.e(TAG, "getWallpapers: Error")
                    }
                } catch (e: HttpException) {
                    Log.e(TAG, "getWallpapers Exception ${e.message}")
                } catch (e: Throwable) {
                    Log.e(
                        TAG, "getWallpapers throwable " + e.localizedMessage!!
                    )

                }
            }
        }
    }

    override fun onWallpaperClick(position: Int) {
        this.findNavController()
            .navigate(
                CategoryWallpapersDirections.actionCategoryWallpapersToWallpaperFragment(
                    wallpapers[position].path,
                    wallpapers[position].id
                )
            )
    }
}