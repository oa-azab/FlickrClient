package com.example.flickerclient.ui

import android.util.Log
import com.example.flickerclient.data.Image
import com.example.flickerclient.data.source.remote.RemoteDataSource
import com.example.flickerclient.ui.adapter.ImagesAdapter

class MainPresenter(val view: MainContract.View) : MainContract.Presenter {

    val adapter = ImagesAdapter()
    val remoteDataSource = RemoteDataSource.getInstance()

    init {
        view.setAdapter(adapter)
    }

    override fun start() {
        Log.d(TAG, "[start]")

        remoteDataSource.getRecentImages(object : RemoteDataSource.ImagesLoaded {
            override fun onLoad(images: List<Image>) {
                Log.d(TAG, "[start] onLoad")
                Log.d(TAG, "[start] onLoad ${images.size}")
                for (image in images) Log.d(TAG, "[start] onLoad ${image.toString()}")
                adapter.swapData(images)
            }

            override fun onFailure() {
                Log.d(TAG, "[start] onFailure")
            }

        })
    }

    companion object {
        private const val TAG = "MainPresenter"
    }
}