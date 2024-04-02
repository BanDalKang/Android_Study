package com.example.introduction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    // 뷰모델 내부에서 설정하는 자료형은 뮤터블로 해서 변경가능하도록 설정
    private val _name = MutableLiveData<String>()
    // 변경되지 않는 데이터를 가져올 떄는 '_'없이 설정
    // 공개적으로 가져오는 변수는 private X, 외부에서도 접근가능하게 설정
    // 값을 직접 LiveData에 접근 X, 뷰모델을 통해 가져오게 설정
    val name: LiveData<String>
        get() = _name

    private val _id = MutableLiveData<String>()
    val id: LiveData<String>
        get() = _id

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() =_password

    private val _passwordCheck = MutableLiveData<String>()
    val passwordCheck: LiveData<String>
        get() =_passwordCheck

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean>
        get() = _isPasswordValid

    private val _isPasswordMatch = MutableLiveData<Boolean>()
    val isPasswordMatch: LiveData<Boolean>
        get() = _isPasswordMatch

    init {
        _isPasswordValid.value = true
        _isPasswordMatch.value = true
    }

    fun setName(name: String) {
        _name.value = name
    }
    fun setId(id: String) {
        _id.value = id
    }
    fun setPassword(password: String) {
        _password.value = password
        isPasswordValid(password)
    }
    fun setPasswordCheck(passwordCheck: String) {
        _passwordCheck.value = passwordCheck
        _isPasswordMatch.value = (passwordCheck ==_password.value)
    }

    // 대문자, 소문자, 특수문자, 숫자를 포함하는 정규식
    private val pattern = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*+=])(?=\\S+$).{8,15}$")
    private fun isPasswordValid(password: String) {
        _isPasswordValid.value = pattern.matches(password)
    }

    fun updateSignUpButtonState(): Boolean {
        val name = _name.value
        val id = _id.value
        val password = _password.value
        val passwordCheck = _passwordCheck.value
        val passwordMatch = _isPasswordMatch.value
        val passwordValid = _isPasswordValid.value

        // 모든 필드가 비어있지 않고, 비밀번호가 강력하면 회원가입 버튼 활성화
        val isSignUpEnabled = !name.isNullOrBlank() &&
                !id.isNullOrBlank() &&
                !password.isNullOrBlank() &&
                !passwordCheck.isNullOrBlank() &&
                passwordValid == true &&
                passwordMatch == true

        return isSignUpEnabled
    }

}