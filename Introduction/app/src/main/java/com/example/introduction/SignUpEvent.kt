package com.example.introduction

sealed interface SignUpEvent {
    data class SignUpSuccess(
        val member: SignUpMember,

        ) : SignUpEvent
}