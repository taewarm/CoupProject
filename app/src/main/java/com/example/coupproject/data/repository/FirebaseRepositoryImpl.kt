package com.example.coupproject.data.repository

import android.content.Context
import android.util.Log
import com.example.coupproject.domain.model.User
import com.example.coupproject.domain.repository.MainRepository
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(private val context: Context) :
    MainRepository {

    override fun getFriend(
        memberId: String,
        callback: OnSuccessListener<DataSnapshot>
    ): Flow<Void> {
        /**roomDb써서 관리할것*/
        return flow {
            Firebase.database.reference.child("users").child(memberId).get()
                .addOnSuccessListener(callback).addOnFailureListener { Log.e(TAG, it.message, it) }
        }
    }

    override fun getMembership(callback: OnSuccessListener<DataSnapshot>): Flow<Void> {
        return flow {
            Log.i(TAG, "getMembership start")
            Firebase.database.reference.child("users").get().addOnSuccessListener(callback)
                .addOnFailureListener {
                    Log.e(TAG, it.message, it)
                }
        }
    }

    override fun setMembership(
        name: String,
        memberId: String,
        callback: OnSuccessListener<Void>
    ): Flow<Unit> {
        return flow {
            Firebase.database.reference.child("users").child(memberId)
                .setValue(User(name, memberId, null, null))
                .addOnSuccessListener(callback)
                .addOnFailureListener { Log.e(TAG, it.message, it) }
        }
    }

    override fun checkPermission(): Flow<Unit> {
        return flow {
            Firebase.database.reference.child("users").setValue(User("이름", "123213213"))
            emit(Unit)
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