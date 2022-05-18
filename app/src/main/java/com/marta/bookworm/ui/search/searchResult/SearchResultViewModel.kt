package com.marta.bookworm.ui.search.searchResult

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
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val db: BookWorm_Database
) : ViewModel() {
    private val _searchResultUIState: MutableStateFlow<SearchResultUIState> =
        MutableStateFlow(SearchResultUIState())
    val searchResultUIState: StateFlow<SearchResultUIState> = _searchResultUIState

    fun search(type: String, searchValue: String) {
        if (type == "title") {
            getByTitle(searchValue)
        } else if (type == "author") {
            getByAuthor(searchValue)
        } else {
            getByTag(searchValue)
        }
    }

    fun getByTitle(searchValue: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    _searchResultUIState.update { SearchResultUIState(isLoading = true) }
                    val posts = networkService.getByTitle(searchValue, "Bearer $myToken")
                    _searchResultUIState.update {
                        SearchResultUIState(
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

    fun getByTag(searchValue: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    _searchResultUIState.update { SearchResultUIState(isLoading = true) }
                    val posts = networkService.getByTag(searchValue, "Bearer $myToken")
                    _searchResultUIState.update {
                        SearchResultUIState(
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

    fun getByAuthor(searchValue: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    _searchResultUIState.update { SearchResultUIState(isLoading = true) }
                    val posts = networkService.getByAuthor(searchValue, "Bearer $myToken")
                    _searchResultUIState.update {
                        SearchResultUIState(
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

    fun updateError(msg: String) {
        _searchResultUIState.update {
            SearchResultUIState(
                isLoading = false,
                isError = true,
                errorMsg = msg
            )
        }
    }

    //--BD
    private suspend fun getMyToken(): String {
        return db.dao().findMyToken().token
    }
}