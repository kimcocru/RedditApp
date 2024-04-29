package com.example.redditapp.network.service

import com.example.redditapp.network.response.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface RedditService {

    @GET(".json")
    suspend fun getRedditPosts(): Response<ApiResponse>
}