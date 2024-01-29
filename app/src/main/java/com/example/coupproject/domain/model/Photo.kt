package com.example.coupproject.domain.model

import java.util.Date

data class Photo(
    val token: String? = "",
    val fileName: String? = "",
    val name: String? = null,
    val date: Date? = null
)
