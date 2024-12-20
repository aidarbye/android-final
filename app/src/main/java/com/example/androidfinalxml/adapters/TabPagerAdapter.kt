package com.example.androidfinalxml.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidfinalxml.fragments.JokeFragment
import com.example.androidfinalxml.fragments.ProfileFragment
import com.example.androidfinalxml.fragments.RandomJokeFragment

class TabPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragments = listOf(
        JokeFragment(),
        RandomJokeFragment(),
        ProfileFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
