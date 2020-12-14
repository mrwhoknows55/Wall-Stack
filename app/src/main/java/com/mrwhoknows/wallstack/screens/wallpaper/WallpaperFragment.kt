package com.mrwhoknows.wallstack.screens.wallpaper

import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.mrwhoknows.wallstack.R
import com.mrwhoknows.wallstack.db.FavWall
import com.mrwhoknows.wallstack.db.FavWallDatabase
import kotlinx.android.synthetic.main.fragment_wallpaper.*
import kotlinx.coroutines.launch

class WallpaperFragment : Fragment(R.layout.fragment_wallpaper) {

    private lateinit var args: WallpaperFragmentArgs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args = WallpaperFragmentArgs.fromBundle(requireArguments())
//        TODO: make it transparent
//        val topToolbar = (requireActivity() as MainActivity).topToolbar
//        topToolbar.visibility = View.GONE
//        setWallFAB.hide()
        setWallFAB.setOnClickListener {
            setWallpaper()
        }

        wallpaperImageView.load(args.wallpaperURL) {
            placeholder(R.drawable.ic_cloud_download)
            error(R.drawable.ic_error_outline)
            size(1920, 1080)
        }


//        TODO: do this when clicked on like btn
        val dao = FavWallDatabase.getInstance(requireContext()).favWallDao()
        lifecycleScope.launch {
//            TODO change to id
            val favWall = FavWall(args.wallpaperURL, args.wallpaperURL)
            dao.addFavWall(favWall)
        }

    }

    private fun setWallpaper() {
        try {
            wallpaperImageView.invalidate()
            val drawable = wallpaperImageView.drawable as BitmapDrawable
            val wallBitmap = drawable.bitmap
            val manager = WallpaperManager.getInstance(activity)
            manager.setBitmap(wallBitmap)
            Toast.makeText(context, "Wallpaper Set!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "$e.localizedMessage}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}