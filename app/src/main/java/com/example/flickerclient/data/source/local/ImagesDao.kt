package com.example.flickerclient.data.source.local

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.support.design.circularreveal.CircularRevealHelper
import com.example.flickerclient.data.Image

@Dao
interface ImagesDao {

    @Query("SELECT * FROM Image")
    fun getImages(): DataSource.Factory<Int, Image>

    @Query("SELECT * FROM Image WHERE id =:id")
    fun getImage(id: String): Image

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(images: List<Image>)

    @Query("DELETE FROM Image")
    fun deleteImages()
}