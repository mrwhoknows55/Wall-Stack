package com.mrwhoknows.wallstack.screens.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mrwhoknows.wallstack.R
import com.mrwhoknows.wallstack.adapter.WallpaperAdapter
import com.mrwhoknows.wallstack.model.Wallpaper
import kotlinx.android.synthetic.main.fragment_wallpaper_list.*
import okhttp3.internal.notify
import kotlin.math.log

private const val TAG = "HomeFragment"

class WallpaperListFragment : Fragment(), WallpaperAdapter.WallpaperListener {

    private lateinit var viewModel: WallpaperListViewModel
    private lateinit var wallpaperAdapter: WallpaperAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        Log.i(TAG, "onCreateView: called")

        Log.i(TAG, "onCreateView: viewModelCalled")
        viewModel = ViewModelProviders.of(this).get(WallpaperListViewModel::class.java)

        return inflater.inflate(R.layout.fragment_wallpaper_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated: called")

        if (viewModel.dataList.value.isNullOrEmpty()) {
            viewModel.getWall()
        }

        val dataListObserver = Observer<List<Wallpaper.Data>> { wallpaperURIList ->
            //update ui
            if (viewModel.isSuccessful && viewModel.dataList.value?.size!! > 0) {
                initRecyclerView(wallpaperURIList)
            } else {
                Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.dataList.observe(viewLifecycleOwner, dataListObserver)
    }

    private fun initRecyclerView(dataList: List<Wallpaper.Data>) {
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        wallpaperAdapter = WallpaperAdapter(dataList, this)
        recyclerView.adapter = wallpaperAdapter
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: called")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: called")
    }

    override fun onWallpaperClick(position: Int) {
        Log.i(TAG, "onWallpaperClick: called")
        this.findNavController()
            .navigate(
                WallpaperListFragmentDirections.actionWallpaperListFragmentToWallpaperFragment(
                    viewModel.dataList.value?.get(position)!!.path
                )
            )
    }
}