package com.example.coupproject.domain.repository

import com.example.coupproject.domain.model.Friend
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getFriend(): Flow<Friend>
    suspend fun checkPermission(): Flow<Void>
}