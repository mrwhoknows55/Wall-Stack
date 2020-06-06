package com.mrwhoknows.wallstack.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mrwhoknows.wallstack.R
import com.mrwhoknows.wallstack.model.Wallpaper

class WallpaperAdapter(private val wallpapers: List<Wallpaper.Data>) :
    RecyclerView.Adapter<WallpaperAdapter.WallpaperHolder>() {

    class WallpaperHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        private val wallpaperImageView: ImageView = itemview.findViewById(R.id.wallpaperImageView)
        private val bar: ProgressBar = itemview.findViewById(R.id.bar)

        fun bind(data: Wallpaper.Data) {
            Glide.with(itemView.context)
                .load(data.thumbs.original)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        bar.visibility = View.GONE
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
                        return false
                    }

                })
                .into(wallpaperImageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperHolder {
        return WallpaperHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.wallpaper_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return wallpapers.size
    }

    override fun onBindViewHolder(holder: WallpaperHolder, position: Int) {
        when (holder) {
            is WallpaperHolder -> {
                holder.bind(wallpapers[position])
            }
        }
    }

}