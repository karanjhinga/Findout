package com.karan.findout.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karan.findout.data.model.Photo
import com.karan.findout.databinding.ListItemPhotoBinding

class PhotoAdapter : ListAdapter<Photo, PhotoAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ListItemPhotoBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.imageUrl = item.thumbnailUrl
        holder.binding.executePendingBindings()
    }

    inner class ViewHolder(val binding: ListItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.thumbnailUrl == newItem.thumbnailUrl
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = true
}
