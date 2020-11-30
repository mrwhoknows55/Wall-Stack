package com.mrwhoknows.wallstack.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavWall::class], version = 1)
abstract class FavWallDatabase : RoomDatabase() {
    abstract fun favWallDao(): FavWallDao

    companion object {
        @Volatile
        private var INSTANCE: FavWallDatabase? = null

        fun getInstance(context: Context): FavWallDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavWallDatabase::class.java,
                    "FavWall"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}