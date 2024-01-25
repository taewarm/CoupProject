package com.example.coupproject.domain.repository

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getFriend(memberId: String,callback: OnSuccessListener<DataSnapshot>): Flow<Void>
    fun getMembership(callback: OnSuccessListener<DataSnapshot>): Flow<Void>
    fun setMembership(
        name: String,
        memberId: String,
        callback: OnSuccessListener<Void>
    ): Flow<Unit>

    fun checkPermission(): Flow<Unit>
}