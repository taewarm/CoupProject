package com.example.coupproject.domain.usecase.main

import com.example.coupproject.domain.repository.MainRepository
import javax.inject.Inject

class GetFriendUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke() = repository.getFriend()
}