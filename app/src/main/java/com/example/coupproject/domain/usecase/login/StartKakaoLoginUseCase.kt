package com.example.coupproject.domain.usecase.login

import com.example.coupproject.domain.repository.KakaoRepository
import com.example.coupproject.view.login.LoginActivity
import com.kakao.sdk.auth.model.OAuthToken
import javax.inject.Inject

class StartKakaoLoginUseCase @Inject constructor(private val repository: KakaoRepository) {
    operator fun invoke(
        activity: LoginActivity,
        callback: (OAuthToken?, Throwable?) -> Unit
    ) = repository.startKakaoLogin(activity, callback)
}