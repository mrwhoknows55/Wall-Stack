package com.mrwhoknows.wallstack.screens.category

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrwhoknows.wallstack.R
import com.mrwhoknows.wallstack.adapter.CategoryListAdapter
import com.mrwhoknows.wallstack.model.Category
import kotlinx.android.synthetic.main.fragment_wallpaper_category_list.*
import kotlin.math.abs

private const val TAG = "WallpaperCategoryListFr"

class WallpaperCategoryListFragment : Fragment(), CategoryListAdapter.CategoryListener {

    private lateinit var categories: MutableList<Category>
    private lateinit var categoryListAdapter: CategoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initList()

        return inflater.inflate(R.layout.fragment_wallpaper_category_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCatRecyclerView(categories)
    }


    private fun initCatRecyclerView(categories: List<Category>) {
        catRecyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            categoryListAdapter =
                CategoryListAdapter(categories, this@WallpaperCategoryListFragment)
            adapter = categoryListAdapter
        }
    }

    private fun initList() {
        val anime = Category("Anime", "https://lol.com")
        val city = Category("City", "https://lol.com")
        val abstract = Category("Abstract", "https://lol.com")
        val material = Category("Material", "https://lol.com")
        val marvel = Category("Marvel", "https://lol.com")
        val landscape = Category("Landscape", "https://lol.com")

        categories = mutableListOf()

        categories.add(anime)
        categories.add(city)
        categories.add(abstract)
        categories.add(material)
        categories.add(marvel)
        categories.add(landscape)
    }

    override fun onCategoryClick(position: Int) {
        Log.i(TAG, "onCategoryClick: $position")
    }

}