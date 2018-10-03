package com.example.flickerclient.data.source.local

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.flickerclient.data.Image

@Dao
interface ImagesDao {

    @Query("SELECT * FROM Image")
    fun getImages(): DataSource.Factory<Int, Image>

    @Insert
    fun insert(images: List<Image>)

    @Query("DELETE FROM Image")
    fun deleteImages()
}