package com.mrwhoknows.wallstack.screens.wallpaper

import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore.Images.Media.getBitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mrwhoknows.wallstack.MainActivity
import com.mrwhoknows.wallstack.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_wallpaper.*
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception

private const val TAG = "WallpaperFragment"

class WallpaperFragment : Fragment() {

    private lateinit var args: WallpaperFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView: called")

        args = WallpaperFragmentArgs.fromBundle(arguments!!)


        return inflater.inflate(R.layout.fragment_wallpaper, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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