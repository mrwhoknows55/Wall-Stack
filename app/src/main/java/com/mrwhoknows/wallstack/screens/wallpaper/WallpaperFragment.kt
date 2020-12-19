package com.mrwhoknows.wallstack.screens.wallpaper

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mrwhoknows.wallstack.MainActivity
import com.mrwhoknows.wallstack.R
import com.mrwhoknows.wallstack.db.FavWall
import com.mrwhoknows.wallstack.db.FavWallDatabase
import kotlinx.android.synthetic.main.fragment_wallpaper.*
import kotlinx.android.synthetic.main.set_wallpaper_sheet.*
import kotlinx.coroutines.launch
import java.io.File

class WallpaperFragment : Fragment(R.layout.fragment_wallpaper) {

    private lateinit var args: WallpaperFragmentArgs
    private lateinit var topToolbar: MaterialToolbar
    private lateinit var bottomNavBar: BottomNavigationView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var wallUri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args = WallpaperFragmentArgs.fromBundle(requireArguments())
//        TODO: make it transparent
        val mainActivity = requireActivity() as MainActivity
        topToolbar = mainActivity.topToolbar
        bottomNavBar = mainActivity.bottomNavBar
        mainActivity.topAppbarLayout.setExpanded(false)
        topToolbar.visibility = View.GONE
        bottomNavBar.visibility = View.GONE

        val bottomSheet = setWallSheet
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        setWallBtn.setOnClickListener {

            setWallpaper(wallUri)
        }

//        TODO Check Permission
        downloadWallpaper.setOnClickListener {
            val fileName = "wallstack-${args.wallpaperId}.jpg"
            val downloadManager: DownloadManager =
                requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val uri = Uri.parse(args.wallpaperURL)
            val request: DownloadManager.Request = DownloadManager.Request(uri)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                "WallStack/$fileName"
            )
            downloadManager.enqueue(request)
            val file = File(
                "${Environment.getExternalStorageDirectory().path}/${Environment.DIRECTORY_PICTURES}/WallStack",
                fileName
            )
            wallUri = Uri.parse(file.absolutePath)
        }

        wallpaperImageView.load(args.wallpaperURL) {
            placeholder(R.drawable.ic_cloud_download)
            error(R.drawable.ic_error_outline)
            size(1920, 1080)
        }


//        TODO: 1 do this when clicked on like btn
        val dao = FavWallDatabase.getInstance(requireContext()).favWallDao()
        lifecycleScope.launch {
//            TODO change to id
            val favWall = FavWall(args.wallpaperURL, args.wallpaperURL)
            dao.addFavWall(favWall)
        }

    }

//      TODO: 2. get back to old method with  home, lock screen and both as option dialog
    private fun setWallpaper(wallUri: Uri) {
        try {
//            TODO old code for ref
//            wallpaperImageView.invalidate()
//            val drawable = wallpaperImageView.drawable as BitmapDrawable
//            val wallBitmap = drawable.bitmap
//            val manager = WallpaperManager.getInstance(activity)
//            manager.setBitmap(wallBitmap)

            val intent = Intent(Intent.ACTION_ATTACH_DATA).apply {
                setDataAndType(wallUri, "image/jpeg")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra("mimeType", "image/*")
            }
            startActivity(
                Intent.createChooser(intent, "Set as: ").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )

        } catch (e: Exception) {
            Toast.makeText(context, "$e.localizedMessage}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onDetach() {
        super.onDetach()
        topToolbar.visibility = View.VISIBLE
        bottomNavBar.visibility = View.VISIBLE
    }
}