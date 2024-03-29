package com.example.coupproject.domain.usecase.main

import com.example.coupproject.domain.repository.MainRepository
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import javax.inject.Inject

class GetFriendUseCase @Inject constructor(private val repository: MainRepository) {
    operator fun invoke(memberId: String, callback: OnSuccessListener<DataSnapshot>) =
        repository.getFriend(memberId, callback)
}