package com.example.flickerclient.data.source.remote

import android.util.Log
import com.example.flickerclient.data.Image
import com.example.flickerclient.data.source.remote.model.GetRecentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class RemoteDataSource private constructor() {

    private val service: FlickerService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.flickr.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(FlickerService::class.java)
    }

    fun getRecentImages(callback: ImagesLoaded, page: Int = 1) {

        val url = service.getRecent("4aa2e6c19b7ef76656d2488879cc3833").request().url().toString()
        Log.d(TAG, "[getRecentImages] url = $url")

        service.getRecent("4aa2e6c19b7ef76656d2488879cc3833", page = page)
                .enqueue(object : Callback<GetRecentResponse> {
                    override fun onResponse(call: Call<GetRecentResponse>, response: Response<GetRecentResponse>) {
                        Log.d(TAG, "[getRecentImages] onResponse")
                        if (!response.isSuccessful) {
                            Log.d(TAG, "[getRecentImages] onResponse request not successful ${response.code()}")
                            callback.onFailure()
                            return
                        }

                        val data = response.body()?.photos?.photo
                        if (data == null) {
                            Log.d(TAG, "[getRecentImages] onResponse data is null")
                            callback.onFailure()
                            return
                        }

                        callback.onLoad(data)
                    }

                    override fun onFailure(call: Call<GetRecentResponse>, t: Throwable) {
                        Log.d(TAG, "[getRecentImages] onFailure")
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