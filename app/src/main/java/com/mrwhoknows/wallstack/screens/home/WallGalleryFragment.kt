package com.mrwhoknows.wallstack.screens.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mrwhoknows.wallstack.R
import com.mrwhoknows.wallstack.adapter.WallpaperListAdapter
import com.mrwhoknows.wallstack.adapter.WallpaperLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_wallpaper_list.*

@AndroidEntryPoint
class WallGalleryFragment : Fragment(R.layout.fragment_wallpaper_list) {

    private val viewModel by viewModels<WallGalleryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = WallpaperListAdapter()

        recyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(requireContext(),2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = WallpaperLoadStateAdapter { adapter.retry() },
            footer = WallpaperLoadStateAdapter { adapter.retry() }
        )

        viewModel.wallpapers.observe(viewLifecycleOwner, Observer { wallpapersPage ->
            adapter.submitData(viewLifecycleOwner.lifecycle, wallpapersPage)
        })
    }
}