package com.example.androidfinalxml.network

import com.example.androidfinalxml.models.ActivityModel
import retrofit2.http.GET

interface ActivityAPI {
    @GET("activity")
    suspend fun getActivity(): ActivityModel
}