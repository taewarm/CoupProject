package com.example.coupproject.domain.usecase.login

import com.example.coupproject.domain.repository.KakaoRepository
import com.kakao.sdk.user.model.AccessTokenInfo
import javax.inject.Inject

class GetAccessTokenInfoUseCase @Inject constructor(private val repository: KakaoRepository) {
    operator fun invoke(callback: (AccessTokenInfo?, Throwable?) -> Unit) =
        repository.getAccessTokenInfo(callback)
}