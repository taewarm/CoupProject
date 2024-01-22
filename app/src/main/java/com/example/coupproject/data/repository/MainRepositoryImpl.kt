package com.example.coupproject.data.repository

import android.content.Context
import android.util.Log
import com.example.coupproject.domain.model.Friend
import com.example.coupproject.domain.repository.MainRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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

    override suspend fun checkPermission(): Flow<Void> {
        return flow {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (!Settings.canDrawOverlays(context)) {
//                    val intent = Intent(
//                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:$packageName")
//                    )
//                }
//            }
        }
    }

    companion object {
        const val TAG = "MainRepositoryImpl"
    }
}