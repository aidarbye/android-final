package com.example.androidfinalxml.network

import com.example.androidfinalxml.models.JokeModel
import com.example.androidfinalxml.models.JokeAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    suspend fun getJokeList(): List<JokeModel> {
        val retrofit = Retrofit.Builder().baseUrl("https://official-joke-api.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(JokeAPI::class.java)
        return service.getData()
    }

    suspend fun getRandomJoke(): JokeModel {
        val retrofit = Retrofit.Builder().baseUrl("https://official-joke-api.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(JokeAPI::class.java)
        return service.getRandomJoke()
    }
}