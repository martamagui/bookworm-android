package com.marta.bookworm.ui.createReview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.api.model.body.review.ReviewBody
import com.marta.bookworm.db.BookWorm_Database
import com.marta.bookworm.ui.createReview.step2.CreateReviewStep2UIState
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
    private val _step2UIState: MutableStateFlow<CreateReviewStep2UIState> = MutableStateFlow(
        CreateReviewStep2UIState()
    )
    val step2UIState: StateFlow<CreateReviewStep2UIState> get() = _step2UIState
    private val _createReviewSharedState: MutableStateFlow<ReviewBody> =
        MutableStateFlow(ReviewBody())
    val createReviewSharedState: StateFlow<ReviewBody> get() = _createReviewSharedState

    fun setStep1Info(title: String, author: String, image: String, score: Double) {
        _createReviewSharedState.update {
            ReviewBody(
                bookAuthor = author,
                bookTitle = title,
                image = image,
                score = score
            )
        }
    }

    fun setStep2Info(description: String) {
        if(description.length>20){
            _createReviewSharedState.update {
                ReviewBody(
                    bookAuthor = createReviewSharedState.value.bookAuthor,
                    bookTitle = createReviewSharedState.value.bookTitle,
                    image = createReviewSharedState.value.image,
                    score = createReviewSharedState.value.score,
                    reviewDescription = description,
                    hastags = createReviewSharedState.value.hastags
                )
            }
            sendReview()
        }else{
            setError("Please, fill description.")
        }
    }

    private fun setError(msg:String){
        _step2UIState.update { CreateReviewStep2UIState(isError = true, errorMsg = msg) }
    }

    private suspend fun getMyToken(): String {
        val token = db.dao().findAllToken()
        Log.e("Detail", token.toString())
        return token[0].token
    }

    private fun sendReview() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    networkService.createReview("Bearer ${myToken}", createReviewSharedState.value)
                }
            } catch (error: Error) {
                Log.e("Review", "Error posting review: $error")
                setError("Failed posting review")
            }
        }
    }

    fun addTag(tag: String) {
        var tags: MutableList<String> = mutableListOf()
        tags.addAll(createReviewSharedState.value.hastags)
        tags.add(tag)
        ReviewBody(
            bookAuthor = createReviewSharedState.value.bookAuthor,
            bookTitle = createReviewSharedState.value.bookTitle,
            image = createReviewSharedState.value.image,
            score = createReviewSharedState.value.score,
            reviewDescription = createReviewSharedState.value.reviewDescription,
            hastags = tags
        )
    }


}