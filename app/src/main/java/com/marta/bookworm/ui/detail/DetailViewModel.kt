package com.marta.bookworm.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.db.BookWorm_Database
import com.marta.bookworm.ui.feed.FeedUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val db: BookWorm_Database
) : ViewModel()  {
    private val _detailUIState: MutableStateFlow<DetailUIState> = MutableStateFlow(DetailUIState())
    val detailUIState: StateFlow<DetailUIState> get() = _detailUIState

    fun getPost(){
        viewModelScope.launch(Dispatchers.IO){
            _detailUIState.update { DetailUIState(isLoading = true) }
            try {
                _detailUIState.update { DetailUIState(isLoading = false, isSuccess = true) }
                val post = networkService.getPostDetail()
            }catch (error: Error){
                updateError("Loading post failed")
            }
        }
    }


    fun updateError(msg: String) {
        _detailUIState.update { DetailUIState(isLoading = false, isError = true, errorMsg = msg) }
    }
}