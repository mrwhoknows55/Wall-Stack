package com.mrwhoknows.wallstack.screens.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrwhoknows.wallstack.MainActivity
import com.mrwhoknows.wallstack.R
import com.mrwhoknows.wallstack.adapter.CategoryListAdapter
import com.mrwhoknows.wallstack.model.Category
import kotlinx.android.synthetic.main.fragment_wallpaper_category_list.*

private const val TAG = "WallpaperCategoryListFr"

class WallpaperCategoryListFragment : Fragment(), CategoryListAdapter.CategoryListener {

    private lateinit var categories: MutableList<Category>
    private lateinit var categoryListAdapter: CategoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val topToolbar = (requireActivity() as MainActivity).topToolbar
        topToolbar.apply {
            visibility = View.VISIBLE
            title = "Categories"
        }

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
        val abstract = Category("Abstract", "https://w.wallhaven.cc/full/73/wallhaven-73vdp3.jpg")
        val animal = Category("Animals", "https://w.wallhaven.cc/full/nk/wallhaven-nk6kpq.jpg")
        val city = Category("City", "https://w.wallhaven.cc/full/4x/wallhaven-4xmr5z.jpg")
        val food = Category("Food", "https://w.wallhaven.cc/full/ne/wallhaven-neyzdw.jpg")
        val landscape = Category("Landscape", "https://w.wallhaven.cc/full/od/wallhaven-od8rem.jpg")
        val material = Category("Material", "https://w.wallhaven.cc/full/4x/wallhaven-4xg7gd.jpg")
        val minimal = Category("Minimal", "https://w.wallhaven.cc/full/76/wallhaven-769q2y.jpg")
        val motivation =
            Category("Motivational", "https://w.wallhaven.cc/full/j8/wallhaven-j81z1p.jpg")
        val pattern = Category("Pattern", "https://w.wallhaven.cc/full/0w/wallhaven-0wx2mr.png")
        val space = Category("Space", "https://w.wallhaven.cc/full/nr/wallhaven-nr2dlj.jpg")
        val sport = Category("Sport", "https://w.wallhaven.cc/full/n6/wallhaven-n6odz6.jpg")

        categories = mutableListOf()
        categories.add(abstract)
        categories.add(animal)
        categories.add(city)
        categories.add(food)
        categories.add(landscape)
        categories.add(material)
        categories.add(minimal)
        categories.add(motivation)
        categories.add(pattern)
        categories.add(space)
        categories.add(sport)
    }

    override fun onCategoryClick(position: Int) {
        Log.i(TAG, "onCategoryClick: $position")
        this.findNavController()
            .navigate(
                WallpaperCategoryListFragmentDirections.actionWallpaperCategoryListFragmentToCategoryWallpapers(
                    categories[position].categoryName
                )
            )
    }

}