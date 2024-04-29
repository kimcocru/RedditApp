package com.example.redditapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.redditapp.R
import com.example.redditapp.databinding.ItemPostBinding
import com.example.redditapp.network.model.RedditPost
import com.example.redditapp.utils.getBitmapFromUrl

class PostListAdapter(val context: Context, private val onItemClicked: AdapterOnClick) :
    RecyclerView.Adapter<PostListAdapter.DataViewHolder>() {

    private lateinit var binding: ItemPostBinding
    var postList = listOf<RedditPost>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataModel = postList[position]
        holder.bind(dataModel, onItemClicked)
    }

    override fun getItemCount(): Int = postList.size

    inner class DataViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: RedditPost, onItemClicked: AdapterOnClick) {
            if (post.data.thumbnail.isNullOrEmpty()) {
                binding.thumbnailImageView.visibility = View.GONE
            } else {
                binding.thumbnailImageView.getBitmapFromUrl(context, post.data.thumbnail)
            }
            binding.titleTextView.text = post.data.title
            binding.upvotesTextView.text = post.data.ups.toString()
            binding.numberCommentsTextView.text = context.getString(
                R.string.reddit_post_list_comments,
                post.data.numComments.toString()
            )
            itemView.setOnClickListener { onItemClicked.onClick(post) }
        }
    }
}