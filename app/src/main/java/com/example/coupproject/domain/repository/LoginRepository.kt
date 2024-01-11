package com.example.coupproject.domain.repository

import com.example.coupproject.view.login.LoginActivity
import com.kakao.sdk.auth.model.OAuthToken
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun startKakaoLogin(activity: LoginActivity,callback: (OAuthToken?, Throwable?) -> Unit): Flow<Void>
}