package com.example.coupproject.domain.model

data class User(
    val name: String? = null,
    val token: String? = null,
    val friend: String? = null,
    val requestFriend: List<User>? = arrayListOf()
)
