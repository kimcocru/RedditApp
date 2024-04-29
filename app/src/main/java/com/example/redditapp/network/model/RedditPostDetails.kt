package com.example.redditapp.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RedditPostDetails(
    val title: String,
    val ups: Int,
    @SerializedName("num_comments")
    val numComments: Int,
    val thumbnail: String? = null,
    @SerializedName("selftext")
    val body: String? = null
) : Serializable