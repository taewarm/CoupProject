package com.example.coupproject.data.repository

import android.content.Context
import android.provider.Settings
import android.util.Log
import com.example.coupproject.domain.model.Friend
import com.example.coupproject.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val context: Context) :
    MainRepository {

    override suspend fun getFriend(): Flow<Friend> {
        /**roomDb써서 관리할것*/
        return flow {
            emit(Friend("name", "id"))
        }
    }

    companion object {
        const val TAG = "MainRepositoryImpl"
    }
}