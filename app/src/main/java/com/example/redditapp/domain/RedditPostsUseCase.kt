package com.example.redditapp.domain

import com.example.redditapp.network.model.RedditPost
import com.example.redditapp.utils.ApiResultHandler
import javax.inject.Inject

class GetRedditPostsUseCase @Inject constructor(private val repository: RedditPostsRepository) :
    IGetRedditPostsUseCase {
    override suspend fun run(): ApiResultHandler<List<RedditPost>>? =
        repository.getRedditPosts()
}

interface IGetRedditPostsUseCase {
    suspend fun run(): ApiResultHandler<List<RedditPost>>?
}