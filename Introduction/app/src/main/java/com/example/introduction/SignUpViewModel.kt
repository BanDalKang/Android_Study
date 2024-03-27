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

    fun isStrongPassword(password: String): Boolean {
        // 대문자, 소문자, 특수문자, 숫자를 포함하는 정규식
        val pattern = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*+=])(?=\\S+$).{8,15}$")
        return pattern.matches(password)
    }

    fun setUserValue(name: String, id: String, password: String) {
        _name.value = name
        _id.value = id
        _password.value = password
    }
}