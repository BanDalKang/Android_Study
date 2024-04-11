package com.example.introduction

data class SignUpErrorUiState(
    val name: SignUpValidUiState,
    val emailId: SignUpValidUiState,
    val passwordInput: SignUpValidUiState,
    val passwordConfirm: SignUpValidUiState,
    val enabled: Boolean,
) {
    companion object {
        fun init() = SignUpErrorUiState(
            name = SignUpValidUiState.Init,
            emailId = SignUpValidUiState.Init,
            passwordInput = SignUpValidUiState.Init,
            passwordConfirm = SignUpValidUiState.Init,
            enabled = false
        )
    }
}

sealed interface SignUpValidUiState {
    // 초기 상태
    object Init : SignUpValidUiState

    // 통과
    object Valid : SignUpValidUiState

    // 이름
    object Name : SignUpValidUiState

    // 이메일
    object EmailBlank : SignUpValidUiState
    object Emailvalid : SignUpValidUiState

    // 비밀번호 입력
    object PasswordInputLength : SignUpValidUiState
    object PasswordInputSpecialCharacters : SignUpValidUiState
    object PasswordInputUpperCase : SignUpValidUiState

    // 비밀번호 확인
    object PasswordConfirm : SignUpValidUiState
}