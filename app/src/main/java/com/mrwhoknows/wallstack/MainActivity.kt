package com.mrwhoknows.wallstack

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.mrwhoknows.wallstack.screens.home.WallGalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val wallgalleryViewModel by viewModels<WallGalleryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NavigationUI.setupWithNavController(bottomNavBar, navHostFragment.findNavController())

        bottomNavBar.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.wallGalleryFragment -> onResume()
            }
        }
    }
}