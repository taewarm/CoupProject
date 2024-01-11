package com.example.coupproject.domain.usecase.login

import com.example.coupproject.domain.repository.LoginRepository
import com.example.coupproject.view.login.LoginActivity
import com.kakao.sdk.auth.model.OAuthToken
import javax.inject.Inject

class StartKakaoLoginUseCase @Inject constructor(private val repository: LoginRepository) {
    suspend operator fun invoke(
        activity: LoginActivity,
        callback: (OAuthToken?, Throwable?) -> Unit
    ) = repository.startKakaoLogin(activity, callback)
}