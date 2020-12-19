package com.mrwhoknows.wallstack

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mrwhoknows.wallstack.screens.home.WallGalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val wallGalleryViewModel by viewModels<WallGalleryViewModel>()
    lateinit var topToolbar: MaterialToolbar
    lateinit var topAppbarLayout: AppBarLayout
    lateinit var bottomNavBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topToolbar = findViewById(R.id.topAppBar)
        bottomNavBar = findViewById(R.id.bottomNavBar)
        topAppbarLayout = findViewById(R.id.topAppBarLayout)

        NavigationUI.setupWithNavController(bottomNavBar, navHostFragment.findNavController())

        bottomNavBar.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.wallGalleryFragment -> onResume()
                R.id.wallpaperCategoryListFragment -> onResume()
                R.id.favWallpaperListFragment -> onResume()
            }
        }
    }
}