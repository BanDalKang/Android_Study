package com.example.introduction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _id = MutableLiveData<String>()
    val id: LiveData<String> = _id

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    fun setName(name: String) {
        _name.value = name
    }

    fun setId(id: String) {
        _id.value = id
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun isValidInput(): Boolean {
        return _name.value?.isNotEmpty() ?: false &&
                _id.value?.isNotEmpty() ?: false &&
                _password.value?.isNotEmpty() ?: false
    }
}