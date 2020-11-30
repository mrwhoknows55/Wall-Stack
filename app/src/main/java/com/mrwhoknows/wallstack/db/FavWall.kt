package com.mrwhoknows.wallstack.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FavWall(
    @PrimaryKey(autoGenerate = false)
    val wallpaperId: String,
    val wallpaperLink: String
)
