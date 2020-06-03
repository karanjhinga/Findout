package com.karan.findout.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karan.findout.data.model.Header
import com.karan.findout.data.model.Restaurant
import com.karan.findout.databinding.ListItemCuisineBinding
import com.karan.findout.databinding.ListItemRestaurantBinding
import com.karan.findout.domain.entity.ItemModel
import com.karan.findout.utils.ItemType

class RestaurantAdapter(
    private val onCuisineClick: ((cuisine: String) -> Unit)? = null,
    private val restaurantClick: (restaurant: Restaurant) -> Unit
) : ListAdapter<ItemModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is Header -> ItemType.CUISINE_HEADER
            is Restaurant -> ItemType.RESTAURANT
            else -> throw IllegalStateException("Unknown view type")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        @ItemType viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ItemType.CUISINE_HEADER ->
                CuisineViewHolder(ListItemCuisineBinding.inflate(inflater, parent, false))
            ItemType.RESTAURANT ->
                RestaurantViewHolder(ListItemRestaurantBinding.inflate(inflater, parent, false))
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Header -> (holder as CuisineViewHolder).bind(item)
            is Restaurant -> (holder as RestaurantViewHolder).bind(item)
        }
    }

    inner class CuisineViewHolder(private val binding: ListItemCuisineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cuisine: Header) {
            binding.apply {
                cuisineName.text = cuisine.name
                cuisineName.setOnClickListener { onCuisineClick?.invoke(cuisine.name) }
                executePendingBindings()
            }
        }
    }

    inner class RestaurantViewHolder(private val binding: ListItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: Restaurant) {
            binding.restaurant = restaurant
            binding.root.setOnClickListener { restaurantClick(restaurant) }
            binding.executePendingBindings()
        }
    }
}

/* Calculating differences between list resulting in performance improvement*/
private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemModel>() {
    override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel) = when {
        (oldItem is Restaurant) && (newItem is Restaurant) -> oldItem.id == newItem.id
        (oldItem is Header) && (newItem is Header) -> oldItem.name == newItem.name
        else -> false
    }

    // passing false cause restaurant data isn't likely to update in single session
    override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel) = true
}
