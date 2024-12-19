package com.example.androidfinalxml.network

import com.example.androidfinalxml.models.JokeModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://official-joke-api.appspot.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(JokeAPI::class.java)

    suspend fun getJokeList(): List<JokeModel> {
        return try {
            service.getData()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getRandomJoke(): JokeModel {
        return try {
            service.getRandomJoke()
        } catch (e: Exception) {
            throw e
        }
    }
}
