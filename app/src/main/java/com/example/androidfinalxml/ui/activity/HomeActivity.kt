package com.example.androidfinalxml.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.example.androidfinalxml.R
import com.example.androidfinalxml.ui.viewmodel.AuthViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Инициализация ViewModel
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        // Если пользователь не залогинен, перенаправляем на AuthActivity
        if (authViewModel.isLoggedIn.value != true) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Логика для главного экрана
        val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            authViewModel.logout()
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
