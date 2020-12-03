package com.mrwhoknows.wallstack.screens.fav

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrwhoknows.wallstack.MainActivity
import com.mrwhoknows.wallstack.R
import com.mrwhoknows.wallstack.adapter.WallpaperAdapter
import com.mrwhoknows.wallstack.db.FavWallDatabase
import kotlinx.android.synthetic.main.fragment_wallpaper_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavWallpaperListFragment : Fragment(R.layout.fragment_fav_wallpaper_list),
    WallpaperAdapter.WallpaperListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var wallpaperAdapter: WallpaperAdapter
    private lateinit var walls: List<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topToolbar = (requireActivity() as MainActivity).topToolbar
        topToolbar.apply {
            visibility = View.VISIBLE
            title = "Favourite Walls"
        }

        val dao = FavWallDatabase.getInstance(requireContext()).favWallDao()

        lifecycleScope.launch {
            walls = dao.getFavWalls()

            withContext(Dispatchers.Main) {
                if (walls.isNotEmpty()) {
                    recyclerView = view.findViewById(R.id.rvFavWallpaper)
                    recyclerView.layoutManager = GridLayoutManager(activity, 2)
                    wallpaperAdapter = WallpaperAdapter(this@FavWallpaperListFragment, walls)
                    recyclerView.adapter = wallpaperAdapter
                }
            }
        }


    }

    override fun onWallpaperClick(position: Int) {
        this.findNavController().navigate(
            FavWallpaperListFragmentDirections.actionFavWallpaperListFragmentToWallpaperFragment(
                walls[position],
                walls[position]
            )
        )
    }

}