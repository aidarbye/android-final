package com.example.androidfinalxml.fragments

import JokeViewModel
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfinalxml.activity.AuthActivity
import com.example.androidfinalxml.adapters.JokeAdapter
import com.example.androidfinalxml.databinding.FragmentProfileBinding
import com.example.androidfinalxml.viewmodel.AuthViewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()
    private val jokeViewModel: JokeViewModel by activityViewModels()

    private lateinit var adapter: JokeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = JokeAdapter(jokeViewModel) { joke, isLiked ->
            if (isLiked) {
                jokeViewModel.saveFavoriteJoke(joke)
            } else {
                jokeViewModel.removeFavoriteJoke(joke)
            }
        }

        binding.listOfFavorites.adapter = adapter
        binding.listOfFavorites.layoutManager = LinearLayoutManager(requireContext())

        binding.logoutButton.setOnClickListener {
            authViewModel.logout()
            val intent = Intent(requireActivity(), AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        jokeViewModel.getFavoriteJokes()

        jokeViewModel.favoriteJokes.observe(viewLifecycleOwner) { jokes ->
            adapter.setItems(jokes)
            adapter.setFavoriteJokes(jokes)
        }

        jokeViewModel.isFavoritesLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            if (!isLoading) {
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        jokeViewModel.favoriteJokes.observe(viewLifecycleOwner) { jokes ->
            if (jokes.isNotEmpty()) {
                binding.listOfFavorites.visibility = View.VISIBLE
                adapter.setItems(jokes)
            } else {
                binding.listOfFavorites.visibility = View.GONE
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

