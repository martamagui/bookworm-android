package com.marta.bookworm.ui.profile.savedPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.db.BookWorm_Database
import com.marta.bookworm.ui.profile.ProfileUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedPostViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val db: BookWorm_Database
) : ViewModel() {
    private val _savedPostsUIState: MutableStateFlow<SavedPostUIState> =
        MutableStateFlow(SavedPostUIState())
    val savedPostUIState: StateFlow<SavedPostUIState> = _savedPostsUIState

    fun getSavedPost() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    val reviews = networkService.getSavedPost("Bearer $myToken")
                    _savedPostsUIState.update {
                        SavedPostUIState(
                            isLoading = false,
                            isSuccess = true,
                            reviews = reviews
                        )
                    }
                }
            } catch (error: Error) {
                updateError("Error retrieveing information")
            }
        }
    }

    //--BD
    private suspend fun getMyToken(): String {
        return db.dao().findMyToken().token
    }

    //-Others
    fun updateError(msg: String) {
        _savedPostsUIState.update {
            SavedPostUIState(
                isLoading = false,
                isError = true,
                errorMsg = msg
            )
        }
    }
}