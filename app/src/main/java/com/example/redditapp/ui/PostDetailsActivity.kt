package com.example.redditapp.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.redditapp.R
import com.example.redditapp.network.model.RedditPost
import com.example.redditapp.network.model.RedditPostDetails
import com.example.redditapp.utils.fromHex
import java.io.Serializable


class PostDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val redditPost: RedditPost? = intent.serializable("RedditPostObject")

        setContent {
            MaterialTheme {
                redditPost?.data?.let { PostDetailsScreen(applicationContext, it) }
            }
        }
    }
}

@Composable
fun PostDetailsScreen(context: Context, postDetails: RedditPostDetails) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Row {
            Text(
                modifier = Modifier.padding(12.dp),
                text = postDetails.ups.toString(),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W700,
                    fontStyle = FontStyle.Normal
                )
            )
            Text(
                modifier = Modifier.padding(start = 0.dp, top = 11.dp, end = 1.dp, bottom = 0.dp),
                text = postDetails.title,
                style = TextStyle(
                    color = Color.fromHex("777696"),
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W800,
                    fontStyle = FontStyle.Normal
                )
            )
        }
        if (postDetails.body?.isNotEmpty() == true) {
            Text(
                modifier = Modifier.padding(start = 60.dp, top = 8.dp, bottom = 16.dp, end = 0.dp),
                text = postDetails.body.toString(),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W400,
                    fontStyle = FontStyle.Normal
                )
            )
        }
        if (!postDetails.thumbnail.isNullOrEmpty()) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 60.dp, top = 16.dp, bottom = 0.dp, end = 16.dp),
                contentScale = ContentScale.Crop,
                model = postDetails.thumbnail,
                contentDescription = stringResource(R.string.reddit_post_image_content_description),
            )
        }

        Text(
            modifier = Modifier.padding(start = 60.dp, top = 8.dp, bottom = 16.dp, end = 0.dp),
            text = context.getString(
                R.string.reddit_post_list_comments, postDetails.numComments.toString()
            ),
            style = TextStyle(
                color = Color.fromHex("777777"),
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W700,
                fontStyle = FontStyle.Normal
            )
        )

    }
}

//Util function to be able to handle the getSerializableExtra deprecation
inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(
        key, T::class.java
    )

    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}