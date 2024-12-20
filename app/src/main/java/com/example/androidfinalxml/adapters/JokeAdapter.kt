package com.example.androidfinalxml.adapters

import JokeViewModel
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfinalxml.databinding.JokeItemBinding
import com.example.androidfinalxml.utils.JokeDiffUtil
import com.example.androidfinalxml.models.JokeModel


class JokeAdapter(
    private val viewModel: JokeViewModel,
    private val onLikeClicked: (JokeModel, Boolean) -> Unit
) : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    private var jokesList: ArrayList<JokeModel> = arrayListOf()
    private var favoriteJokes: List<JokeModel> = listOf()

    fun setItems(jokes: List<JokeModel>) {
        val diffUtil = JokeDiffUtil(jokesList, jokes)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        jokesList = jokes as ArrayList<JokeModel>
        diffResult.dispatchUpdatesTo(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFavoriteJokes(favorites: List<JokeModel>) {
        favoriteJokes = favorites
        notifyDataSetChanged()
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

                val isLiked = favoriteJokes.any { it.id == joke.id }

                likeButton.text = if (isLiked) "Unlike" else "Like"
                likeButton.setBackgroundColor(
                    if (isLiked)
                        Color.BLACK
                    else
                        Color.RED
                )

                likeButton.setOnClickListener {
                    val newIsLiked = !isLiked

                    likeButton.text = if (newIsLiked) "Unlike" else "Like"
                    likeButton.setBackgroundColor(
                        if (newIsLiked)
                            Color.BLACK
                        else
                            Color.RED
                    )

                    onLikeClicked(joke, newIsLiked)
                }
            }
        }
    }
}