package com.marta.bookworm.ui.createReview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.api.model.body.review.ReviewBody
import com.marta.bookworm.api.model.response.ReviewResponse
import com.marta.bookworm.db.BookWorm_Database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateReviewSharedViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val db: BookWorm_Database
) : ViewModel() {
    private val _createReviewSharedState: MutableStateFlow<ReviewBody> =
        MutableStateFlow(ReviewBody())
    val createReviewSharedState: StateFlow<ReviewBody> get() = _createReviewSharedState

    fun setStep1Info(title: String, author: String, image: String, score: Double) {
        _createReviewSharedState.update {
            ReviewBody(
                bookAuthor = author,
                bookTitle = title,
                image = image,
                score = score.toDouble()
            )
        }
    }

    fun setStep2Info(description: String, tags: List<String>) {
        _createReviewSharedState.update {
            ReviewBody(
                bookAuthor = createReviewSharedState.value.bookAuthor,
                bookTitle = createReviewSharedState.value.bookTitle,
                image = createReviewSharedState.value.image,
                reviewDescription = description,
                hastags = tags
            )
        }
    }

    private suspend fun getMyToken(): String {
        val token = db.dao().findAllToken()
        Log.e("Detail", token.toString())
        return token[0].token
    }

    fun sendReview() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    networkService.createReview("Bearer ${myToken}", createReviewSharedState.value)
                }
            } catch (error: Error) {
                Log.e("Review", "Error posting review: $error")
            }
        }
    }
}