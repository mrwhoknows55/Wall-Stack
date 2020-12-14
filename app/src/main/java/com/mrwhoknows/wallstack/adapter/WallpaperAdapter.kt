package com.mrwhoknows.wallstack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
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

        fun bind(wallLink: String) {

            itemView.setOnClickListener(this)

            wallpaperImageView.load(wallLink) {
                crossfade(true)
                placeholder(R.drawable.ic_cloud_download)
                error(R.drawable.ic_error_outline)
            }
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