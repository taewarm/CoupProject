package com.example.coupproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coupproject.domain.usecase.login.HasAccessTokenUseCase
import com.example.coupproject.domain.usecase.main.CheckPermissionUseCase
import com.example.coupproject.domain.usecase.main.GetFriendUseCase
import com.example.coupproject.domain.usecase.main.GetMembershipUseCase
import com.example.coupproject.domain.usecase.main.SetMembershipUseCase
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFriendUseCase: GetFriendUseCase,
    private val hasAccessTokenUseCase: HasAccessTokenUseCase,
    private val checkPermissionUseCase: CheckPermissionUseCase,
    private val getMembershipUseCase: GetMembershipUseCase,
    private val setMembershipUseCase: SetMembershipUseCase
) : ViewModel() {
    private val _hasMembership = MutableSharedFlow<Boolean>()
    val hasMembership: SharedFlow<Boolean> = _hasMembership

    fun getFriend(memberId: String, callback: OnSuccessListener<DataSnapshot>) {
        viewModelScope.launch(Dispatchers.Main) {
            getFriendUseCase(memberId, callback)
                .catch { exception ->
                    Log.e(TAG, exception.message, exception)
                }.collect()
        }
    }

    fun getMembership(callback: OnSuccessListener<DataSnapshot>) {
        viewModelScope.launch {
            getMembershipUseCase(callback).catch { exception ->
                Log.e(TAG, exception.message, exception)
            }
        }
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}