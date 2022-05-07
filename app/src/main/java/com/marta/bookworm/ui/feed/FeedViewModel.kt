package com.marta.bookworm.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.db.BookWorm_Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val db: BookWorm_Database
) : ViewModel() {
    private val _feedUIState: MutableStateFlow<FeedUIState> =  MutableStateFlow(FeedUIState())
    val feedUIState: StateFlow<FeedUIState> get() = _feedUIState

    fun getAllFeedPosts(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _feedUIState.update { FeedUIState(isLoading = true) }
                val posts = networkService.getAllPosts();
                _feedUIState.update { FeedUIState(isLoading = false, feedList = posts) }
            }catch (error: Error){
                updateError("Loading post failed")
            }
        }
    }

    fun updateError(msg: String){
        _feedUIState.update { FeedUIState(isLoading = false, isError = true, errorMsg = msg ) }
    }
    
}