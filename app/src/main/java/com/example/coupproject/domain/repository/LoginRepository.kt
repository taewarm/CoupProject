package com.example.coupproject.domain.repository

import com.kakao.sdk.auth.model.OAuthToken
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun startKakaoLogin(): Flow<OAuthToken>
}