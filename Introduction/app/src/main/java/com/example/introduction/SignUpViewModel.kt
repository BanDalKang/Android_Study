package com.example.introduction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() =_password

    private val _passwordCheck = MutableLiveData<String>()
    val passwordCheck: LiveData<String>
        get() =_passwordCheck

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean>
        get() = _isEmailValid

    private val _isNameValid = MutableLiveData<Boolean>()
    val isNameValid: LiveData<Boolean>
        get() = _isNameValid

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean>
        get() = _isPasswordValid

    private val _isPasswordMatch = MutableLiveData<Boolean>()
    val isPasswordMatch: LiveData<Boolean>
        get() = _isPasswordMatch

    init {
        _isNameValid.value = true
        _isEmailValid.value = true
        _isPasswordValid.value = true
        _isPasswordMatch.value = true
    }

    fun setName(name: String) {
        _name.value = name
        isNameValid(name)
    }
    fun setId(email: String) {
        _email.value = email
        isEmailValid(email)
    }
    fun setPassword(password: String) {
        _password.value = password
        isPasswordValid(password)
    }
    fun setPasswordCheck(passwordCheck: String) {
        _passwordCheck.value = passwordCheck
        _isPasswordMatch.value = (passwordCheck ==_password.value)
    }

    // 한글 이름 정규식
    private val namePattern = Regex("^[가-힣]{2,10}\$")
    private fun isNameValid(name: String) {
        _isNameValid.value = namePattern.matches(name)
    }

    // 이메일 정규식
    private val emailPattern = Regex("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}\$")
    private fun isEmailValid(email: String) {
        _isEmailValid.value = emailPattern.matches(email)
    }

    // 대문자, 소문자, 특수문자, 숫자를 포함하는 정규식
    private val passwordPattern = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*+=])(?=\\S+$).{8,15}$")
    private fun isPasswordValid(password: String) {
        _isPasswordValid.value = passwordPattern.matches(password)
    }

    fun updateSignUpButtonState(): Boolean {
        val name = _name.value
        val nameValid = _isNameValid.value
        val email = _email.value
        val emailValid = _isEmailValid.value
        val password = _password.value
        val passwordCheck = _passwordCheck.value
        val passwordMatch = _isPasswordMatch.value
        val passwordValid = _isPasswordValid.value

        // 모든 필드가 비어있지 않고, 유효성 만족하면 회원가입 버튼 활성화
        val isSignUpEnabled = !name.isNullOrBlank() &&
                nameValid == true &&
                !email.isNullOrBlank() &&
                emailValid == true &&
                !password.isNullOrBlank() &&
                !passwordCheck.isNullOrBlank() &&
                passwordValid == true &&
                passwordMatch == true

        return isSignUpEnabled
    }
}