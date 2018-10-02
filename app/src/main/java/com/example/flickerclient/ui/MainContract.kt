package com.example.flickerclient.ui

import com.example.flickerclient.ui.adapter.ImagesAdapter

interface MainContract {

    interface View {

        fun setAdapter(adapter: ImagesAdapter)

        fun showData()
    }

    interface Presenter {
        fun start()
    }
}