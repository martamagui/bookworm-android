package com.marta.bookworm.ui.createReview

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.db.BookWorm_Database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateReviewStep1ViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val db: BookWorm_Database
) : ViewModel()  {
    private val _createReviewUIState: MutableStateFlow<CreateReviewStep1UIState> = MutableStateFlow(
        CreateReviewStep1UIState()
    )
    val createReviewUIState: StateFlow<CreateReviewStep1UIState> get() = _createReviewUIState
    private val imageRef = Firebase.storage.reference

    fun uploadImage(uri: Uri){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val image =
                    imageRef.child("images/review/${UUID.randomUUID()}")
                        .putFile(uri)
            } catch (error: Error) {
                Log.e("Firebase", "$error")
            }
        }
    }
}