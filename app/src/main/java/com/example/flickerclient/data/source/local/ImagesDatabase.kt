package com.example.flickerclient.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.flickerclient.data.Image

@Database(entities = arrayOf(Image::class), version = 1)
abstract class ImagesDatabase : RoomDatabase() {

    abstract fun imagesDao(): ImagesDao

    companion object {

        private var INSTANCE: ImagesDatabase? = null

        private val sLock = Any()

        fun getInstance(context: Context): ImagesDatabase {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ImagesDatabase::class.java, "flickerimages.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}