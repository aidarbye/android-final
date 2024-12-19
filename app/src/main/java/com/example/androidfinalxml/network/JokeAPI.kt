package com.example.androidfinalxml.network

import com.example.androidfinalxml.models.JokeModel
import retrofit2.http.GET

interface JokeAPI {
    @GET("random_ten")
    suspend fun getData(): List<JokeModel>

    @GET("random_joke")
    suspend fun getRandomJoke(): JokeModel
}