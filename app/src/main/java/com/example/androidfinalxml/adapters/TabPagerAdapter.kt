package com.example.androidfinalxml.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.androidfinalxml.fragments.JokeFragment
import com.example.androidfinalxml.fragments.RandomJokeFragment

class TabPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {

    private val fragments = listOf(
        JokeFragment(),
        RandomJokeFragment()
    )

    private val titles = listOf("Jokes", "Random Joke")

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }


}
