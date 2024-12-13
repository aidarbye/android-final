package com.example.androidfinalxml.models

import com.example.androidfinalxml.models.Joke
import retrofit2.http.GET

interface JokeAPI {
    @GET("random_ten")
    suspend fun getData(): List<Joke>

    @GET("random_joke")
    suspend fun getRandomJoke(): Joke
}