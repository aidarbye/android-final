package com.example.androidfinalxml.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfinalxml.models.JokeModel
import com.example.androidfinalxml.network.ApiClient
import kotlinx.coroutines.launch

class JokeViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _jokes = MutableLiveData<List<JokeModel>>()
    val jokes: LiveData<List<JokeModel>> get() = _jokes

    fun getJokeList() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val jokes = ApiClient.getJokeList()
                _jokes.postValue(jokes)
            } catch (e: Exception) {
                _jokes.postValue(emptyList())
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}