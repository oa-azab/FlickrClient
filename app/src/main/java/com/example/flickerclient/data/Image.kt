package com.example.flickerclient.data

data class Image(
        var id: String,
        var secret: String,
        var server: String,
        var farm: String){

    fun getUrl(): String = "https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg"
}