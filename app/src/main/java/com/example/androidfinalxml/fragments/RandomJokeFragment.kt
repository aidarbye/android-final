package com.example.androidfinalxml.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.androidfinalxml.databinding.JokeItemBinding
import com.example.androidfinalxml.network.ApiClient
import kotlinx.coroutines.launch

class RandomJokeFragment : Fragment() {
    private var _binding: JokeItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = JokeItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.likeButton.visibility = View.GONE

        showLoading(true)

        lifecycleScope.launch {
            try {
                val joke = ApiClient.getRandomJoke()
                showLoading(false)
                binding.jokeSetUp.text = joke.setup
                binding.jokePunchline.text = joke.punchline
            } catch (e: Exception) {
                showLoading(false)
                binding.jokeSetUp.text = "Error"
                binding.jokePunchline.text = "Could not load joke"
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.jokeSetUp.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.jokePunchline.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
