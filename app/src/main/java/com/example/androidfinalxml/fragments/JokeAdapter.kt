package com.example.androidfinalxml.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfinalxml.databinding.FragmentJokesBinding
import com.example.androidfinalxml.databinding.JokeItemBinding
import com.example.androidfinalxml.models.JokeModel


class JokeAdapter: RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    private var jokesList: ArrayList<JokeModel> = arrayListOf()

    fun setItems(jokes: List<JokeModel>) {
        val diffUtil = JokeDiffUtil(jokesList, jokes)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        jokesList = jokes as ArrayList<JokeModel>
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder(
            JokeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return jokesList.size
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(jokesList[position])
    }

    inner class JokeViewHolder(private val binding: JokeItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(joke: JokeModel) {
            with(binding) {
                binding.jokeSetUp.text = joke.setup
                binding.jokePunchline.text = joke.punchline
            }
        }

    }



}