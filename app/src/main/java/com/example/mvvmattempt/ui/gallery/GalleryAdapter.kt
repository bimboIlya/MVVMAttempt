package com.example.mvvmattempt.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmattempt.data.Picture
import com.example.mvvmattempt.databinding.ItemPictureBinding

class GalleryAdapter :
    ListAdapter<Picture, GalleryAdapter.GalleryViewHolder>(PictureItemCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPictureBinding.inflate(inflater)

        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }



    class GalleryViewHolder(private val binding: ItemPictureBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Picture) {
            binding.run {
                picture = item
                executePendingBindings()
            }

        }
    }
}

private class PictureItemCallback : DiffUtil.ItemCallback<Picture>() {
    override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean =
        oldItem == newItem
}