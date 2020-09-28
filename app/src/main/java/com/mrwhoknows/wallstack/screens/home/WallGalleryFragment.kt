package com.mrwhoknows.wallstack.screens.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WallGalleryFragment : Fragment() {

    private val viewModel by viewModels<WallGalleryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.wallpapers.observe(viewLifecycleOwner, Observer { wallpapersPage ->

        })
    }
}