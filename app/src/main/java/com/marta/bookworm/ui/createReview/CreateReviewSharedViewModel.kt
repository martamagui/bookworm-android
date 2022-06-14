package com.marta.bookworm.ui.createReview

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateReviewSharedViewModel {
    private val _createReviewSharedUIState: MutableStateFlow<CreateReviewSharedUIState> = MutableStateFlow(
        CreateReviewSharedUIState()
    )
    val createReviewSharedUIState: StateFlow<CreateReviewSharedUIState> get() = _createReviewSharedUIState

    fun setStep1Info(title: String, author: String, image: String){

    }
}