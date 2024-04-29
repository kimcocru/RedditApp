package com.example.redditapp.domain

import com.example.redditapp.network.service.RedditService
import javax.inject.Inject

class RedditPostsDataSource @Inject constructor(private val service: RedditService) {
    suspend fun getRedditPosts() = service.getRedditPosts()
}