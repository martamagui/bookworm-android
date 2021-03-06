package com.marta.bookworm.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.db.BookWorm_Database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val db: BookWorm_Database
) : ViewModel() {
    private val _feedUIState: MutableStateFlow<FeedUIState> = MutableStateFlow(FeedUIState())
    val feedUIState: StateFlow<FeedUIState> get() = _feedUIState

    fun getAllFeedPosts() {
        updateLoading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    _feedUIState.update { FeedUIState(isLoading = true) }
                    val posts = networkService.getAllPosts("Bearer $myToken");
                    _feedUIState.update {
                        FeedUIState(
                            isLoading = false,
                            isSuccess = true,
                            feedList = posts
                        )
                    }
                }
            } catch (error: Error) {
                updateError("Loading post failed")
            }
        }
    }

    private fun updateLoading() {
        _feedUIState.update { FeedUIState(isLoading = true) }
    }

    private fun updateError(msg: String) {
        _feedUIState.update { FeedUIState(isLoading = false, isError = true, errorMsg = msg) }
    }

    fun likePost(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    networkService.likeDislike("Bearer ${myToken}", postId)
                }
            } catch (error: Error) {
                updateError("Couldn't update like for this post.")
            }
        }
    }

    fun saveUnsavePost(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    networkService.saveUnsavePost("Bearer  $myToken", postId)
                }
            } catch (error: Error) {
                updateError("Couldn't update like for this post.")
            }
        }
    }

    //--BD
    private suspend fun getMyToken(): String {
        return db.dao().findMyToken().token
    }

}