package com.example.flickerclient.data.source.remote

import com.example.flickerclient.data.source.remote.model.GetRecentResponse
import com.example.flickerclient.util.Const.FLICKR_API_KEY
import com.example.flickerclient.util.Const.FLICKR_API_METHOD_NAME
import com.example.flickerclient.util.Const.FLICKR_API_REPONSE_FORMAT
import com.example.flickerclient.util.Const.PAGE_SIZE
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickerService {

    @GET("services/rest/")
    fun getImages(
            @Query("page") page: Int = 1,
            @Query("api_key") apiKey: String = FLICKR_API_KEY,
            @Query("method") method: String = FLICKR_API_METHOD_NAME,
            @Query("format") format: String = FLICKR_API_REPONSE_FORMAT,
            @Query("nojsoncallback") flag: Int = 1,
            @Query("per_page") perPage: Int = PAGE_SIZE
    ): Call<GetRecentResponse>
}