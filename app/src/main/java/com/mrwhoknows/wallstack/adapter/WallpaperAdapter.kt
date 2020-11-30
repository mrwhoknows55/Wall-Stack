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

class WallpaperAdapter(
    private val wallpaperListener: WallpaperListener
) : RecyclerView.Adapter<WallpaperAdapter.WallpaperHolder>() {

    private var wallpapers: List<Wallpaper.Data>
    private var walls: MutableList<String>

    init {
        wallpapers = listOf()
        walls = mutableListOf()
    }

    constructor(wallpapers: List<Wallpaper.Data>, wallpaperListener: WallpaperListener) : this(
        wallpaperListener
    ) {
        this.wallpapers = wallpapers
        wallpapers.forEach {
            this.walls.add(it.thumbs.original)
        }
    }

    constructor(wallpaperListener: WallpaperListener, walls: List<String>) : this(
        wallpaperListener
    ) {
        this.walls = walls.toMutableList()
        this.wallpapers = emptyList()
    }

    class WallpaperHolder(itemview: View, private val wallpaperListener: WallpaperListener) :
        RecyclerView.ViewHolder(itemview), View.OnClickListener {

        private val wallpaperImageView: ImageView =
            itemview.findViewById(R.id.wallpaperItemImageView)
        private val bar: ProgressBar = itemview.findViewById(R.id.bar)

        fun bind(wallLink: String) {

            itemView.setOnClickListener(this)

            Glide.with(itemView.context)
                .load(wallLink)
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

        override fun onClick(v: View?) {
            wallpaperListener.onWallpaperClick(adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperHolder {
        return WallpaperHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.wallpaper_item, parent, false),
            wallpaperListener
        )
    }

    override fun getItemCount(): Int {
        return walls.size
    }

    override fun onBindViewHolder(holder: WallpaperHolder, position: Int) {
        holder.bind(walls[position])
    }


    interface WallpaperListener {
        fun onWallpaperClick(position: Int)
    }

}