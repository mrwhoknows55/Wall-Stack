package com.mrwhoknows.wallstack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrwhoknows.wallstack.R

class WallpaperLoadStateAdapter(private val retryFun: () -> Unit) :
    LoadStateAdapter<WallpaperLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.wallpaper_load_state_footer, parent, false)
        )


    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val progressBar: ProgressBar = itemView.findViewById(R.id.wallpaperLoadProgressBar)
        private val errorText: TextView = itemView.findViewById(R.id.wallpaperLoadErrorText)
        private val retryBtn: Button = itemView.findViewById(R.id.wallpaperLoadErrorBtn)

        init {
            retryBtn.setOnClickListener {
                retryFun.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            progressBar.isVisible = loadState is LoadState.Loading
            retryBtn.isVisible = loadState !is LoadState.Loading
            errorText.isVisible = loadState !is LoadState.Loading
        }
    }
}