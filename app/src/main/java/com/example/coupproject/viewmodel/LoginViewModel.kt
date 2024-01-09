package com.example.coupproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coupproject.domain.usecase.StartKakaoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val startKakaoLoginUseCase: StartKakaoLoginUseCase
) : ViewModel() {

    fun startKakaoLogin() {
        viewModelScope.launch {
            startKakaoLoginUseCase.invoke()
                .catch { exception -> Log.e("LoginViewModel", exception.message, exception) }
                .collect {
                    Log.i("LoginViewModel", "$it")
                }
        }
    }
}