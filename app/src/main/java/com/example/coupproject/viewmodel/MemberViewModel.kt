package com.example.coupproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coupproject.domain.usecase.main.SetMembershipUseCase
import com.google.android.gms.tasks.OnSuccessListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val setMembershipUseCase: SetMembershipUseCase
) : ViewModel() {

    fun setMembership(name: String, memberId: String, callback: OnSuccessListener<Void>) {
        viewModelScope.launch {
            setMembershipUseCase.invoke(name, memberId, callback)
                .catch { exception ->
                    Log.e(TAG, exception.message, exception)
                }.collect {
                    Log.i(TAG, "Success SetMemberShip")
                }
        }
    }


    companion object {
        const val TAG = "MemberViewModel"
    }
}