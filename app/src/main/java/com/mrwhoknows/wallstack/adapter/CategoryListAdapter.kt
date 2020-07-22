package com.mrwhoknows.wallstack.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mrwhoknows.wallstack.R
import com.mrwhoknows.wallstack.model.Category
import kotlinx.android.synthetic.main.category_item.view.*
import kotlinx.android.synthetic.main.wallpaper_item.view.*

class CategoryListAdapter(
    private val categories: List<Category>,
    private val mCategoryListener: CategoryListener
) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {


    class CategoryViewHolder(itemView: View, private val categoryListener: CategoryListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {


        fun bind(category: Category) {
            itemView.setOnClickListener(this)
            itemView.categoryNameText.text = category.categoryName
            Glide.with(itemView.context)
                .load(category.categoryWallLink)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemView.catBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemView.catBar.visibility = View.GONE
                        return false
                    }

                })
                .into(itemView.catItemImageView)
        }

        override fun onClick(v: View?) {
            categoryListener.onCategoryClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false),
            mCategoryListener
        )
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        when (holder) {
            is CategoryViewHolder -> {
                holder.bind(categories[position])
            }
        }
    }

    interface CategoryListener {
        fun onCategoryClick(position: Int)
    }
}