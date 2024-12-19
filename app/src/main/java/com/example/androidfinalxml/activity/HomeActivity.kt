package com.example.androidfinalxml.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.androidfinalxml.R
import com.example.androidfinalxml.viewmodel.AuthViewModel
import com.example.androidfinalxml.adapters.TabPagerAdapter
import com.google.android.material.tabs.TabLayout

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

        // Инициализация ViewPager и TabLayout
        val viewPager: ViewPager = findViewById(R.id.viewPager)
        val adapter = TabPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)

        viewPager.adapter = adapter

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
    }
}
