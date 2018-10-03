package com.example.flickerclient.data.source.remote

import com.example.flickerclient.data.source.remote.model.GetRecentResponse
import com.example.flickerclient.util.Const.PAGE_SIZE
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickerService {

    @GET("services/rest/")
    fun getRecent(
            @Query("api_key") apiKey: String,
            @Query("method") method: String = "flickr.photos.search",
            @Query("tags") tags: String = "nature,port,mono,golden",
            @Query("format") format: String = "json",
            @Query("sort") sort: String = "interestingness-desc",
            @Query("nojsoncallback") flag: Int = 1,
            @Query("page") page: Int = 1,
            @Query("per_page") perPage: Int = PAGE_SIZE
    ): Call<GetRecentResponse>
}