package com.example.flickerclient.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Image(
        @PrimaryKey
        var id: String,
        var secret: String,
        var server: String,
        var farm: String) {

    fun getUrl(): String = "https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg"
}