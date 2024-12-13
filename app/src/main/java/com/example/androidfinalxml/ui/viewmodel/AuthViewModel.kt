package com.example.androidfinalxml.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        _isLoggedIn.value = firebaseAuth.currentUser != null
    }

    fun logout() {
        firebaseAuth.signOut()
        _isLoggedIn.value = false
    }
}
