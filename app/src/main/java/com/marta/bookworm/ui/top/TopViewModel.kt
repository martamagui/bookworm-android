package com.marta.bookworm.ui.top

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
class TopViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val db: BookWorm_Database
) : ViewModel() {
    private var _topUIState: MutableStateFlow<TopUIState> = MutableStateFlow(TopUIState())
    val topUIState: StateFlow<TopUIState> get() = _topUIState

    fun getTopInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    val response = networkService.getTopBooks("Bearer ${myToken}")
                    _topUIState.update {
                        TopUIState(
                            isSuccess = true,
                            isLoading = false,
                            topResponse = response
                        )
                    }
                }
            } catch (error: Error) {
                updateError("Couldn't update follow.")
            }
        }
    }

    fun updateError(msg: String) {
        _topUIState.update { TopUIState(isLoading = false, isError = true, errorMsg = msg) }
    }

    //--BD
    private suspend fun getMyToken(): String {
        return db.dao().findMyToken().token
    }
}