package com.example.coupproject.domain.repository

import com.example.coupproject.view.login.LoginActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.AccessTokenInfo
import kotlinx.coroutines.flow.Flow

interface KakaoRepository {
    fun startKakaoLogin(
        activity: LoginActivity,
        callback: (OAuthToken?, Throwable?) -> Unit
    ): Flow<Void>

    fun hasAccessToken(): Flow<Boolean>
    fun getAccessTokenInfo(callback: (tokenInfo: AccessTokenInfo?, error: Throwable?) -> Unit): Flow<Unit>

}