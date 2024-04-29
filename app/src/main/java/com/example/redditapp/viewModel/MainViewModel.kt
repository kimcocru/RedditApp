package com.example.redditapp.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.redditapp.R
import com.example.redditapp.domain.GetRedditPostsUseCase
import com.example.redditapp.utils.ApiResultHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GetRedditPostsUseCase,
    application: Application
) : BaseViewModel(application) {

    private val _apiErrorMessage: MutableLiveData<String> = MutableLiveData("")
    val apiErrorMessage: LiveData<String> = _apiErrorMessage

    fun getPosts() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        try {
            when (
                val response = useCase.run()
            ) {
                is ApiResultHandler.Success -> {
                    emit(response.value)
                }

                is ApiResultHandler.ApiError -> {
                    _apiErrorMessage.value =
                        context.applicationContext.getString(R.string.reddit_post_list_api_error)
                }

                else -> {
                    emit(emptyList())
                }
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}

