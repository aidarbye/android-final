package com.example.androidfinalxml.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

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

    suspend fun signInWithEmailAndPasswordSuspend(
        email: String,
        password: String
    ): AuthResult =
        suspendCancellableCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(task.result)
                    } else {
                        continuation.resumeWithException(task.exception ?: Exception("Неизвестная ошибка"))
                    }
                }
        }
}
