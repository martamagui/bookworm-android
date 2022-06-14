package com.marta.bookworm.ui.createReview.step1

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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CreateReviewStep1ViewModel : ViewModel() {
    private val _createReviewUIState: MutableStateFlow<CreateReviewStep1UIState> = MutableStateFlow(
        CreateReviewStep1UIState()
    )
    val createReviewUIState: StateFlow<CreateReviewStep1UIState> get() = _createReviewUIState
    private val imageRef = Firebase.storage.reference

    fun uploadImage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (createReviewUIState.value.imageToUpload != null) {
                    val ref = imageRef.child("images/review/${UUID.randomUUID()}")
                    ref.putFile(createReviewUIState.value.imageToUpload!!)
                        .continueWithTask { task ->
                            if (!task.isSuccessful) {
                                setError("Failed image upload")
                                task.exception?.let { throw it }
                            }
                            ref.downloadUrl
                        }.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                _createReviewUIState.update {
                                    CreateReviewStep1UIState(
                                        isSuccess = true,
                                        imageLink = "${task.result}"
                                    )
                                }
                                return@addOnCompleteListener
                            }
                            setError("Failed image upload")
                        }
                }
            } catch (error: Error) {
                Log.e("Firebase", "$error")
            }
        }
    }

    fun setSelectedImage(data: Uri) {
        _createReviewUIState.update {
            CreateReviewStep1UIState(imageToUpload = data)
        }
    }

    private fun setError(msg: String) {
        _createReviewUIState.update {
            CreateReviewStep1UIState(
                isError = true,
                errorMsg = msg
            )
        }
    }
}