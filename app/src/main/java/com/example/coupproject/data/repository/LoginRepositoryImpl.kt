package com.example.coupproject.data.repository

import android.content.Context
import android.util.Log
import com.example.coupproject.domain.repository.LoginRepository
import com.example.coupproject.view.login.LoginActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(val context: Context) : LoginRepository {
    override suspend fun startKakaoLogin(): Flow<OAuthToken> {
        var oAuthToken: OAuthToken? = null
        return flow {
            if (!UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                throw IllegalAccessException("KakaoTalk Not Install")
            }
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                error?.let {
                    throw IllegalAccessException("login Fail ${it.message}")
                }
                token?.let {
                    Log.i(LoginActivity.TAG, "login success ${it.accessToken}")
                    oAuthToken = it
                }
            }
            oAuthToken?.let { emit(it) }
        }
    }
}