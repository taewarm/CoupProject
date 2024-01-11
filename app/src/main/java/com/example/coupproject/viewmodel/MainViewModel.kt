package com.example.coupproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coupproject.domain.model.Friend
import com.example.coupproject.domain.usecase.main.GetFriendUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFriendUseCase: GetFriendUseCase
) : ViewModel() {
    private val _friend = MutableSharedFlow<Friend>()
    val friend: SharedFlow<Friend> = _friend

    fun getFriend() {
        viewModelScope.launch {
            getFriendUseCase()
                .catch { exception ->
                    Log.e(TAG, exception.message, exception)
                }
                .collect {
                    _friend.emit(it)
                }
        }
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}