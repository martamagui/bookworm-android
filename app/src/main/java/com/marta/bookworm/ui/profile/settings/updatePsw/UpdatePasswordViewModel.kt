package com.marta.bookworm.ui.profile.settings.updatePsw

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.api.model.body.user.UserBody
import com.marta.bookworm.db.BookWorm_Database
import com.marta.bookworm.ui.common.validatePassword
import com.marta.bookworm.ui.loginAndSignUp.signup.step2.SignUpStep2UIState
import com.marta.bookworm.ui.profile.savedPost.SavedPostUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val db: BookWorm_Database
) : ViewModel() {
    private val _updatePasswordUIState: MutableStateFlow<UpdatePasswordUIState> =
        MutableStateFlow(UpdatePasswordUIState())
    val updatePasswordUIState: StateFlow<UpdatePasswordUIState> = _updatePasswordUIState

     fun updatePassword(pasword: String, pasword2: String) {
         val validatePassword = validatePassword(pasword, pasword2)
         if(validatePassword){
          doUpdateRequest(pasword)
         }else{
             setError("Password must contain a character (a-z),(A-Z) and (0-9)")
         }
    }

    private fun doUpdateRequest(pasword: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    val response =
                        networkService.updatePassword("Bearer $myToken", UserBody(password = pasword))
                    if(response!=null){
                        setSuccess()
                    }
                }
            } catch (error: Error) {
                Log.e("UpdatePassword", "$error")
                setError("Error updating password.")
            }
        }
    }

    private fun setError(error: String) {
        _updatePasswordUIState.update { UpdatePasswordUIState(isError = true, errorMsg = error) }
    }
    private fun setSuccess(){
        _updatePasswordUIState.update { UpdatePasswordUIState(isSuccess = true) }
    }

    //--BD
    private suspend fun getMyToken(): String {
        return db.dao().findMyToken().token
    }


}