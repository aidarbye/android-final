package com.example.androidfinalxml.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.androidfinalxml.R
import com.example.androidfinalxml.viewmodel.AuthViewModel

class MainActivity : AppCompatActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Наблюдаем за состоянием авторизации
        authViewModel.isLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                // Если пользователь авторизован, переходим на HomeActivity
                if (javaClass != HomeActivity::class.java) {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish() // Закрываем текущую активность
                }
            } else {
                // Если пользователь не авторизован, переходим на AuthActivity
                if (javaClass != AuthActivity::class.java) {
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
