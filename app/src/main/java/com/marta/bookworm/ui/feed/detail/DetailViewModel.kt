package com.marta.bookworm.ui.feed.detail

import android.util.Log
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
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val db: BookWorm_Database
) : ViewModel()  {
    private val _detailUIState: MutableStateFlow<DetailUIState> = MutableStateFlow(DetailUIState())
    val detailUIState: StateFlow<DetailUIState> get() = _detailUIState

    fun getPost(id: String){
        viewModelScope.launch(Dispatchers.IO){
            _detailUIState.update { DetailUIState(isLoading = true) }
            try {
                val token = "Bearer "+getMyToken()
                val post = networkService.getPostDetail(id,token)
                _detailUIState.update { DetailUIState(isLoading = false, isSuccess = true, review = post) }
            }catch (error: Error){
                updateError("Loading post failed")
            }
        }
    }
    private suspend fun getMyToken(): String{
        val token = db.dao().findAllToken()
        Log.e("Detail", token.toString())
        return token[0].token
    }

    fun likePost(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if(myToken!=null){
                    networkService.likeDislike( "Bearer ${myToken}", postId)
                }
            }catch (error: Error){
                updateError("Couldn't update like for this post.")
            }
        }
    }

    fun saveUnsavePost(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken =  getMyToken()
                if(myToken!=null){
                    networkService.saveUnsavePost("Bearer  $myToken", postId)
                }
            }catch (error: Error){
                updateError("Couldn't update like for this post.")
            }
        }
    }

    fun updateError(msg: String) {
        _detailUIState.update { DetailUIState(isLoading = false, isError = true, errorMsg = msg) }
    }
}