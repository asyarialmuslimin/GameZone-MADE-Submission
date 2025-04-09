package com.saifur.gamezone.favourite

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.saifur.gamezone.core.domain.model.FavouriteGame
import com.saifur.gamezone.favourite.databinding.ItemFavouriteBinding

class FavouriteAdapter(private val onClick:(Int) -> Unit) : RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {
    private val favouriteList = arrayListOf<FavouriteGame>()

    fun setData(data:List<FavouriteGame>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = favouriteList.size
            override fun getNewListSize(): Int = data.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return favouriteList[oldItemPosition].id == data[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return favouriteList[oldItemPosition] == data[newItemPosition]
            }
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        favouriteList.clear()
        favouriteList.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding : ItemFavouriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:FavouriteGame) {
            val radius = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                16f,
                binding.root.context.resources.displayMetrics
            ).toInt()

            Glide.with(binding.root.context)
                .load(item.backgroundImage)
                .apply(RequestOptions().transform(RoundedCorners(radius)))
                .into(binding.imgGame)

            binding.txtGameName.text = item.name
            binding.txtGameCategory.text = item.category
            binding.txtRating.text = item.rating
            binding.root.setOnClickListener {
                onClick(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = favouriteList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favouriteList[position])
    }
}