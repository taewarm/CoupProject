package com.example.coupproject.domain.usecase.main

import com.example.coupproject.domain.repository.MainRepository
import com.google.android.gms.tasks.OnSuccessListener
import javax.inject.Inject

class SetMembershipUseCase @Inject constructor(private val repository: MainRepository) {
    operator fun invoke(name: String, memberId: String, callback: OnSuccessListener<Void>) =
        repository.setMembership(name, memberId, callback)
}