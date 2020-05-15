package com.example.mvvmattempt.ui.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmattempt.data.Post
import com.example.mvvmattempt.databinding.ItemPostBinding

class PostsListAdapter : ListAdapter<Post, PostsListAdapter.PostViewHolder>(ItemPostCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater)

        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                navigateToPost(it)
            }
        }

        private fun navigateToPost(v: View) {
            binding.post?.let {
                val direction = PostsListFragmentDirections.actionPostListFragmentToPostFragment(it)
                v.findNavController().navigate(direction)
            }
        }

        fun bind(item: Post) {
            binding.run {
                post = item
                executePendingBindings()
            }
        }
    }
}


private class ItemPostCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
}
