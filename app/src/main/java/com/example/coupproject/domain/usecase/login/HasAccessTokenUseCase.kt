package com.example.coupproject.domain.usecase.login

import com.example.coupproject.domain.repository.KakaoRepository
import javax.inject.Inject

class HasAccessTokenUseCase @Inject constructor(private val repository: KakaoRepository) {
    operator fun invoke() = repository.hasAccessToken()
}