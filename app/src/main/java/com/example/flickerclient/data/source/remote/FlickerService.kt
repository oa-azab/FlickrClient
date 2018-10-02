package com.example.flickerclient.data.source.remote

import com.example.flickerclient.data.source.remote.model.GetRecentResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickerService {

    @GET("services/rest/")
    fun getRecent(
            @Query("method") method: String = "flickr.photos.getRecent",
            @Query("api_key") apiKey: String,
            @Query("format") format: String = "json",
            @Query("nojsoncallback") flag: Int = 1
    ): Call<GetRecentResponse>
}