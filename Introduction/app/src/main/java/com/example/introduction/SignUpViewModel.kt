package com.example.introduction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.introduction.SignUpValidExtension.includeSpecialCharacters
import com.example.introduction.SignUpValidExtension.includeUpperCase
import com.example.introduction.SignUpValidExtension.validEmail

class SignUpViewModel : ViewModel() {
    // user
    private val _errorUiState: MutableLiveData<SignUpErrorUiState> =
        MutableLiveData(SignUpErrorUiState.init())
    val errorUiState: LiveData<SignUpErrorUiState>
        get() = _errorUiState

    private val _event: MutableLiveData<SignUpEvent> = MutableLiveData()
    val event: LiveData<SignUpEvent>
        get() = _event

    fun checkValidName(text: String) {
        _errorUiState.value = errorUiState.value?.copy(
            name = if (text.isBlank()) {
                SignUpValidUiState.Name
            } else {
                SignUpValidUiState.Valid
            }
        )
    }

    fun checkValidEmail(text: String) {
        _errorUiState.value = errorUiState.value?.copy(
            emailId = when {
                text.isBlank() -> SignUpValidUiState.EmailBlank
                text.validEmail().not() -> SignUpValidUiState.Emailvalid

                else -> SignUpValidUiState.Valid
            }
        )
    }

    fun checkValidPasswordInput(text: String) {
        _errorUiState.value = errorUiState.value?.copy(
            passwordInput = when {
                text.length < 8 || text.length > 15 -> SignUpValidUiState.PasswordInputLength
                text.includeSpecialCharacters().not() -> SignUpValidUiState.PasswordInputSpecialCharacters
                text.includeUpperCase().not() -> SignUpValidUiState.PasswordInputUpperCase

                else -> SignUpValidUiState.Valid
            }
        )
    }

    fun checkValidPasswordConfirm(
        text: String,
        confirm: String
    ) {
        _errorUiState.value = errorUiState.value?.copy(
            passwordConfirm = if (text != confirm) {
                SignUpValidUiState.PasswordConfirm
            } else {
                SignUpValidUiState.Valid
            }
        )
    }

    fun isConfirmButtonEnable() {
        val currentState = errorUiState.value ?: return
        val isEnabled = currentState.name == SignUpValidUiState.Valid &&
                currentState.emailId == SignUpValidUiState.Valid &&
                currentState.passwordInput == SignUpValidUiState.Valid &&
                currentState.passwordConfirm == SignUpValidUiState.Valid

        _errorUiState.value = currentState.copy(enabled = isEnabled)
    }

    fun onClick(member: SignUpMember) {
        _event.value = SignUpEvent.SignUpSuccess(member)
    }
}