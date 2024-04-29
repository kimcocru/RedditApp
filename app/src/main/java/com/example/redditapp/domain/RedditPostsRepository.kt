package com.example.redditapp.domain

import com.example.redditapp.network.model.RedditPost
import com.example.redditapp.network.response.ApiResponse
import com.example.redditapp.utils.ApiResultHandler
import retrofit2.Response
import javax.inject.Inject

class RedditPostsRepository @Inject constructor(
    private val dataSource: RedditPostsDataSource,
) {

    private suspend fun callService(): Response<ApiResponse> {
        return dataSource.getRedditPosts()
    }

    suspend fun getRedditPosts(): ApiResultHandler<List<RedditPost>>? {
        val response = callService()
        return if (response.isSuccessful && response.body() != null) {
            response.body()?.let { ApiResultHandler.Success(it.data.children) }
        } else {
            ApiResultHandler.ApiError(response.code().toString(), response.errorBody().toString())
        }
    }
}