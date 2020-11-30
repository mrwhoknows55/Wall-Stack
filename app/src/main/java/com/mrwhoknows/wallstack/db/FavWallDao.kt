package com.mrwhoknows.wallstack.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FavWallDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavWall(favWall: FavWall)

    @Query("SELECT wallpaperLink FROM FavWall")
    suspend fun getFavWalls(): List<String>
}

