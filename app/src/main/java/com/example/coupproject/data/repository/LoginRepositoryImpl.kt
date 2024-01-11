package com.example.coupproject.data.repository

import android.content.Context
import com.example.coupproject.domain.repository.LoginRepository
import com.example.coupproject.view.login.LoginActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val context: Context) :
    LoginRepository {
    override suspend fun startKakaoLogin(
        activity: LoginActivity,
        callback: (OAuthToken?, Throwable?) -> Unit
    ): Flow<Void> {
        return flow {
            if (!UserApiClient.instance.isKakaoTalkLoginAvailable(activity)) {
                throw IllegalAccessException("KakaoTalk Not Install")
            }
            UserApiClient.instance.loginWithKakaoTalk(activity, callback = callback)
        }
    }

    companion object {
        const val TAG = "LoginRepositoryImpl"
    }
}