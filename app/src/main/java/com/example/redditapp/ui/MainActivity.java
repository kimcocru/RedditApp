package com.example.redditapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.redditapp.R;
import com.example.redditapp.databinding.ActivityMainBinding;
import com.example.redditapp.network.model.RedditPost;
import com.example.redditapp.viewModel.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements AdapterOnClick {

    private MainViewModel viewModel;
    private ActivityMainBinding binding;

    private PostListAdapter adapter;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        initViews();
        observe();
    }

    private void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        binding.redditPosts.addItemDecoration(itemDecorator);
        binding.redditPosts.setLayoutManager(layoutManager);
        adapter = new PostListAdapter(this.getApplicationContext(), this);
        binding.redditPosts.setAdapter(adapter);
    }

    private void observe() {
        viewModel.getPosts().observe(this, response -> {
            binding.progress.setVisibility(View.GONE);
            if (response.size() > 0) {
                adapter.setPostList(response);
            } else {
                binding.noItemsTextView.setVisibility(View.VISIBLE);
                binding.noItemsTextView.setText(R.string.reddit_post_list_no_items);
            }
        });

        viewModel.getApiErrorMessage().observe(this, response -> {
            if (!response.isEmpty()) {
                binding.progress.setVisibility(View.GONE);
                binding.noItemsTextView.setVisibility(View.VISIBLE);
                binding.noItemsTextView.setText(R.string.reddit_post_list_api_error);
            }
        });
    }

    @Override
    public void onClick(@NonNull RedditPost item) {
        Intent postDetails = new Intent(this, PostDetailsActivity.class);
        postDetails.putExtra("RedditPostObject", item);
        startActivity(postDetails);
    }
}

