package com.example.coupproject.domain.usecase

import com.example.coupproject.domain.repository.LoginRepository
import javax.inject.Inject

class StartKakaoLoginUseCase @Inject constructor(private val repository: LoginRepository) {
    suspend operator fun invoke() = repository.startKakaoLogin()
}