package com.example.coupproject.data.repository

import android.content.Context
import com.example.coupproject.domain.repository.KakaoRepository
import com.example.coupproject.view.login.LoginActivity
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.AccessTokenInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(private val context: Context) :
    KakaoRepository {
    private val userApiClient: UserApiClient = UserApiClient.instance
    private val tokenManagerProvider: TokenManagerProvider = TokenManagerProvider.instance
    private val authApiClient = AuthApiClient.instance

    override fun startKakaoLogin(
        activity: LoginActivity,
        callback: (OAuthToken?, Throwable?) -> Unit
    ): Flow<Void> {
        return flow {
            if (!userApiClient.isKakaoTalkLoginAvailable(activity)) {
                throw IllegalAccessException("KakaoTalk Not Install")
            }
            userApiClient.loginWithKakaoTalk(activity, callback = callback)
        }
    }

    override fun hasAccessToken(): Flow<Boolean> {
        return flow {
//            if (authApiClient.hasToken()) {
//                emit(true)
//                userApiClient.accessTokenInfo(callback)
//            } else {
//                Log.e(TAG, "authApiClient.hasToken() = false")
//                emit(false)
//            }
            emit(authApiClient.hasToken())
        }
    }

    override fun getAccessTokenInfo(callback: (tokenInfo: AccessTokenInfo?, error: Throwable?) -> Unit): Flow<Unit> {
        return flow { userApiClient.accessTokenInfo(callback) }
    }

    companion object {
        const val TAG = "LoginRepositoryImpl"
        const val LOGIN_ACCESS_TOKEN = 0
        const val LOGOUT_ACCESS_TOKEN = 1

    }
}