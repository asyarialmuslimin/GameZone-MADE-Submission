package com.saifur.gamezone.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saifur.gamezone.core.domain.model.Game
import com.saifur.gamezone.databinding.ItemGameBinding

class GameListAdapter(private val onClick: (Int) -> Unit) : RecyclerView.Adapter<GameListAdapter.ViewHolder>() {
    private val gameList = ArrayList<Game>()

    fun setData(newListData : List<Game>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = gameList.size
            override fun getNewListSize(): Int = newListData.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return gameList[oldItemPosition].id == newListData[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return gameList[oldItemPosition] == newListData[newItemPosition]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        gameList.clear()
        gameList.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding:ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(game:Game) {
            binding.txtGameTitle.text = game.name
            Glide.with(binding.root.context)
                .load(game.backgroundImage)
                .into(binding.imgGame)
            binding.txtRating.text = game.rating.toString()

            binding.root.setOnClickListener {
                onClick(game.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = gameList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(game = gameList[position])
    }
}
