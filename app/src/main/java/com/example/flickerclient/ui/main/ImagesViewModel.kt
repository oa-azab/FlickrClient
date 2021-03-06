package com.example.flickerclient.ui.main

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

/**
 * This ViewModel holds LiveData containers of UI data
 */
class ImagesViewModel(private val app: Application,
                      private val imagesDao: ImagesDao,
                      private val showMessage: (msg: String) -> Unit)
    : AndroidViewModel(app) {

    val images: LiveData<PagedList<Image>>
    val refreshStateLoading = MutableLiveData<Boolean>()
    private val remoteDataSource = RemoteDataSource.getInstance()

    init {
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
        refreshStateLoading.value = true
        remoteDataSource.getImages(object : RemoteDataSource.ImagesLoaded {
            override fun onLoad(images: List<Image>) {
                Log.d(TAG, "[onLoad] ${images.size}")

                AppExecutors.diskIO.execute {
                    imagesDao.deleteImages()
                    imagesDao.insert(images)
                }

                // reset next page
                Prefs.setInt(app.applicationContext, PREF_LAST_PAGE, 1)
                refreshStateLoading.value = false
            }

            override fun onFailure() {
                Log.d(TAG, "[onFailure]")
                refreshStateLoading.value = false
                showMessage.invoke("Refresh failed, please try again")
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