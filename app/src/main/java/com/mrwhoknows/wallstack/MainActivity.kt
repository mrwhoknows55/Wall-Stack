package com.mrwhoknows.wallstack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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