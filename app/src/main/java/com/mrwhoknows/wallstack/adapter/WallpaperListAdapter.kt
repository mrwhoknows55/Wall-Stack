package com.mrwhoknows.wallstack.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mrwhoknows.wallstack.R
import com.mrwhoknows.wallstack.model.Wallpaper

class WallpaperListAdapter(private val listener: OnItemClickListener) :
        PagingDataAdapter<Wallpaper.Data, WallpaperListAdapter.WallpaperViewHolder>(WALLPAPER_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder =
            WallpaperViewHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.wallpaper_item, parent, false)
            )

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null)
            holder.bind(currentItem)
    }

    inner class WallpaperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.wallpaperItemImageView)
        private val bar: ProgressBar = itemView.findViewById(R.id.bar)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null)
                        listener.onItemClick(item)
                }
            }
        }

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
                    .into(imageView)
        }

    }

    interface OnItemClickListener {
        fun onItemClick(data: Wallpaper.Data)
    }

    companion object {

        private val WALLPAPER_COMPARATOR = object : DiffUtil.ItemCallback<Wallpaper.Data>() {
            override fun areItemsTheSame(oldItem: Wallpaper.Data, newItem: Wallpaper.Data) =
                    oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: Wallpaper.Data, newItem: Wallpaper.Data) =
                    oldItem == newItem
        }

    }
}