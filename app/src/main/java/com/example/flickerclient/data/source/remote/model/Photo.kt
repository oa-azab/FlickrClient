package com.example.flickerclient.data.source.remote.model

import com.example.flickerclient.data.Image

class Photo(var page: Int,
            var pages: Int,
            var perpage: Int,
            var total: Int,
            var photo: List<Image>)