package com.example.androidfinalxml.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfinalxml.R
import com.example.androidfinalxml.databinding.FragmentJokesBinding

class JokeFragment : Fragment() {
    private var _binding: FragmentJokesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JokeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJokesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = JokeAdapter()
        binding.jokeRV.adapter = adapter
        binding.jokeRV.layoutManager = LinearLayoutManager(requireContext())

        viewModel.jokes.observe(viewLifecycleOwner){
            adapter.setItems(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getJokeList()
    }

}

