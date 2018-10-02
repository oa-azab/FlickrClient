package com.example.flickerclient.data.source.remote.model

import com.google.gson.annotations.SerializedName

class GetRecentResponse(
        var photos: Photo,
        @SerializedName("stat")
        var status: String)