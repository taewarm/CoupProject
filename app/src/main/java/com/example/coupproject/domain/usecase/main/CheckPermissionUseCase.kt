package com.example.coupproject.domain.usecase.main

import com.example.coupproject.domain.repository.MainRepository
import javax.inject.Inject

class CheckPermissionUseCase @Inject constructor(private val repository: MainRepository) {
    operator fun invoke() = repository.checkPermission()
}