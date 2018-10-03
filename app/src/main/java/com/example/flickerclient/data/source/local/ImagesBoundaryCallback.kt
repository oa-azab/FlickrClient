package com.example.flickerclient.data.source.local

import android.arch.paging.PagedList
import android.content.Context
import android.util.Log
import com.example.flickerclient.data.Image
import com.example.flickerclient.data.source.remote.RemoteDataSource
import com.example.flickerclient.util.AppExecutors
import com.example.flickerclient.util.Const.PAGE_MAX
import com.example.flickerclient.util.Const.PREF_PAGE
import com.example.flickerclient.util.Prefs

class ImagesBoundaryCallback(private val context: Context,
                             private val imagesDao: ImagesDao)
    : PagedList.BoundaryCallback<Image>() {

    private val remoteDataSource = RemoteDataSource.getInstance()

    override fun onItemAtEndLoaded(itemAtEnd: Image) {
        val nextPage = Prefs.getInt(context, PREF_PAGE, 2)
        Log.d(TAG, "[onItemAtEndLoaded] nextPage=$nextPage")
        remoteDataSource.getRecentImages(object : RemoteDataSource.ImagesLoaded {
            override fun onLoad(images: List<Image>) {
                Log.d(TAG, "[onLoad] ${images.size}")

                // insert data to database
                AppExecutors.diskIO.execute { imagesDao.insert(images) }

                // update next page index
                Prefs.setInt(context, PREF_PAGE, if (nextPage == PAGE_MAX) 1 else (nextPage + 1))
            }

            override fun onFailure() {
                Log.d(TAG, "[onFailure]")
            }

        })
    }

    companion object {
        private const val TAG = "ImagesBoundaryCallback"
    }

}
