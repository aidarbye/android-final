package com.example.androidfinalxml.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfinalxml.models.JokeModel
import com.example.androidfinalxml.network.ApiClient
import kotlinx.coroutines.launch

class JokeViewModel: ViewModel() {
    private val _jokes = MutableLiveData<List<JokeModel>>()
    public val jokes get() = _jokes

    fun getJokeList(){
        viewModelScope.launch {
            _jokes.postValue(ApiClient.getJokeList())
        }
    }
}