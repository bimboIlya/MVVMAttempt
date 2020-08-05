package com.example.mvvmattempt.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmattempt.data.Comment
import com.example.mvvmattempt.databinding.ItemCommentBinding

/**
 * Adapter for RecyclerView in [PostFragment]
 */
class CommentListAdapter :
    ListAdapter<Comment, CommentListAdapter.CommentViewHolder>(CommentItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCommentBinding.inflate(inflater)

        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comment) {
            binding.run {
                comment = item
                executePendingBindings()
            }
        }
    }
}


private class CommentItemCallback : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
        oldItem == newItem
}
