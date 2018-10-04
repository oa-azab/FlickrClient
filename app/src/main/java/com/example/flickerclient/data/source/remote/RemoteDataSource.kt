package com.example.flickerclient.data.source.remote

import android.util.Log
import com.example.flickerclient.data.Image
import com.example.flickerclient.data.source.remote.model.GetRecentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource private constructor() {

    private val service: FlickerService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(FlickerService::class.java)
    }

    fun getImages(callback: ImagesLoaded, page: Int = 1) {

        val url = service.getImages(page).request().url().toString()
        Log.d(TAG, "[getImages] url = $url")

        service.getImages(page)
                .enqueue(object : Callback<GetRecentResponse> {
                    override fun onResponse(call: Call<GetRecentResponse>, response: Response<GetRecentResponse>) {
                        Log.d(TAG, "[getImages] onResponse")
                        if (!response.isSuccessful) {
                            Log.d(TAG, "[getImages] onResponse request not successful ${response.code()}")
                            callback.onFailure()
                            return
                        }

                        val data = response.body()?.photos?.photo
                        if (data == null) {
                            Log.d(TAG, "[getImages] onResponse data is null")
                            callback.onFailure()
                            return
                        }

                        callback.onLoad(data)
                    }

                    override fun onFailure(call: Call<GetRecentResponse>, t: Throwable) {
                        Log.d(TAG, "[getImages] onFailure")
                        callback.onFailure()
                    }
                })
    }

    interface ImagesLoaded {
        fun onLoad(images: List<Image>)

        fun onFailure()
    }

    companion object {

        private const val TAG = "RemoteDataSource"
        private const val BASE_URL = "https://api.flickr.com"

        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource {
            if (INSTANCE == null) {
                synchronized(RemoteDataSource::class.java) {
                    INSTANCE = RemoteDataSource()
                }
            }
            return INSTANCE!!
        }
    }
}