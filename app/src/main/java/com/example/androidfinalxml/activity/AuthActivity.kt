package com.example.androidfinalxml.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidfinalxml.R
import com.example.androidfinalxml.viewmodel.AuthViewModel
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.android.play.integrity.internal.s
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class AuthActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val loginButton: Button = findViewById(R.id.loginButton)
        emailEditText = this.findViewById(R.id.emailEditText)
        passwordEditText = this.findViewById(R.id.passwordEditText)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        authViewModel.isLoggedIn.observe(this, Observer { isLoggedIn ->
            if (isLoggedIn) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        emailEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {
                passwordEditText.requestFocus()
                true
            } else {
                false
            }
        }

        passwordEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {
                passwordEditText.clearFocus()
                hideKeyboard(v)
                true
            } else {
                false
            }
        }

        loginButton.setOnClickListener {
            if (emailEditText.text.isEmpty() || passwordEditText.text.isEmpty()) {
                Toast.makeText(
                    this,
                    "Поля не могут быть пустыми",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            login()
        }
    }

    private fun hideKeyboard(view: android.view.View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun login() {

        val email: String = emailEditText.text.toString()
        val password: String = passwordEditText.text.toString()

        authViewModel.firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    authViewModel.checkAuthStatus()
                } else {
                    when (task.exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            Toast.makeText(this, "Неверные учетные данные. Пожалуйста, проверьте ваш email и пароль.", Toast.LENGTH_SHORT).show()
                        }
                        is FirebaseAuthInvalidUserException -> {
                            Toast.makeText(this, "Аккаунт с таким email не найден.", Toast.LENGTH_SHORT).show()
                        }
                        is FirebaseAuthWeakPasswordException -> {
                            Toast.makeText(this, "Пароль слишком слабый. Пожалуйста, выберите более сложный пароль.", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this, "Ошибка авторизации. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }

    }
}
