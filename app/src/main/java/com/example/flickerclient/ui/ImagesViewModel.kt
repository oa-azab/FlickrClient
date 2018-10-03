package com.example.flickerclient.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.util.Log
import com.example.flickerclient.data.Image
import com.example.flickerclient.data.source.local.ImagesBoundaryCallback
import com.example.flickerclient.data.source.local.ImagesDao
import com.example.flickerclient.data.source.remote.RemoteDataSource
import com.example.flickerclient.util.AppExecutors
import com.example.flickerclient.util.Const.PAGE_SIZE
import com.example.flickerclient.util.Const.PREF_LAST_PAGE
import com.example.flickerclient.util.Prefs

class ImagesViewModel(private val app: Application, private val imagesDao: ImagesDao) : AndroidViewModel(app) {

    val images: LiveData<PagedList<Image>>
    val showRetry = MutableLiveData<Boolean>()
    private val remoteDataSource = RemoteDataSource.getInstance()

    init {
        showRetry.value = false
        val factory: DataSource.Factory<Int, Image> = imagesDao.getImages()
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setPageSize(PAGE_SIZE)
        }.build()
        images = LivePagedListBuilder(factory, config)
                .setBoundaryCallback(ImagesBoundaryCallback(app.applicationContext, imagesDao))
                .build()
    }

    fun refresh() {
        Log.d(TAG, "[refresh]")
        showRetry.value = false
        remoteDataSource.getRecentImages(object : RemoteDataSource.ImagesLoaded {
            override fun onLoad(images: List<Image>) {
                Log.d(TAG, "[onLoad] ${images.size}")

                AppExecutors.diskIO.execute {
                    imagesDao.deleteImages()
                    imagesDao.insert(images)
                }

                // reset next page
                Prefs.setInt(app.applicationContext, PREF_LAST_PAGE, 1)
            }

            override fun onFailure() {
                Log.d(TAG, "[onFailure]")
                showRetry.value = true
            }
        })
    }

    fun delete() {
        Log.d(TAG, "[delete]")
        AppExecutors.diskIO.execute { imagesDao.deleteImages() }
    }

    companion object {
        private const val TAG = "ImagesViewModel"
    }
}