package com.example.coupproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coupproject.domain.usecase.login.StartKakaoLoginUseCase
import com.example.coupproject.view.login.LoginActivity
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val startKakaoLoginUseCase: StartKakaoLoginUseCase
) : ViewModel() {
    private val _loginResult = MutableSharedFlow<OAuthToken?>()
    val loginResult: SharedFlow<OAuthToken?> = _loginResult

    fun startKakaoLogin(activity: LoginActivity,callback: (OAuthToken?, Throwable?) -> Unit) {
        viewModelScope.launch {
            startKakaoLoginUseCase.invoke(activity,callback)
                .catch { exception ->
                    Log.e("LoginViewModel", exception.message, exception)
                }
                .collect {
                    Log.i(TAG, "ViewModel Collect")
                }
        }
    }

    companion object {
        const val TAG = "LoginViewModel"
    }
}