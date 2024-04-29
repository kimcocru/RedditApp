package com.example.redditapp.ui

import com.example.redditapp.network.model.RedditPost

interface AdapterOnClick {
    fun onClick(item: RedditPost)
}