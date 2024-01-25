package com.example.coupproject.domain.usecase.main

import com.example.coupproject.domain.repository.MainRepository
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import javax.inject.Inject

class GetMembershipUseCase @Inject constructor(private val repository: MainRepository) {
    operator fun invoke(callback: OnSuccessListener<DataSnapshot>) =
        repository.getMembership(callback)
}