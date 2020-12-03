package com.mrwhoknows.wallstack.screens.wallpaper

import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mrwhoknows.wallstack.MainActivity
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
        val topToolbar = (requireActivity() as MainActivity).topToolbar
        topToolbar.visibility = View.GONE
        setWallFAB.hide()
        setWallFAB.setOnClickListener {
            setWallpaper()
        }

        Glide.with(this)
            .load(args.wallpaperURL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    bar.visibility = View.GONE
                    setWallFAB.hide()
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    bar.visibility = View.GONE
                    setWallFAB.show()
                    return false
                }
            })
            .into(wallpaperImageView)


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
            Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
        }
    }
}