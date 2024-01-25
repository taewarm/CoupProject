package com.example.coupproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coupproject.domain.usecase.login.GetAccessTokenInfoUseCase
import com.example.coupproject.domain.usecase.login.HasAccessTokenUseCase
import com.example.coupproject.domain.usecase.login.StartKakaoLoginUseCase
import com.example.coupproject.domain.usecase.main.GetFriendUseCase
import com.example.coupproject.domain.usecase.main.GetMembershipUseCase
import com.example.coupproject.view.login.LoginActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.AccessTokenInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val startKakaoLoginUseCase: StartKakaoLoginUseCase,
    private val hasAccessTokenUseCase: HasAccessTokenUseCase,
    private val getAccessTokenInfoUseCase: GetAccessTokenInfoUseCase,
    private val getMembershipUseCase: GetMembershipUseCase,
    private val getFriendUseCase: GetFriendUseCase,
) : ViewModel() {
    private val _loginResult = MutableSharedFlow<Boolean>()
    val loginResult: SharedFlow<Boolean> = _loginResult

    fun startKakaoLogin(activity: LoginActivity, callback: (OAuthToken?, Throwable?) -> Unit) {
        viewModelScope.launch {
            startKakaoLoginUseCase.invoke(activity, callback)
                .catch { exception ->
                    Log.e(TAG, exception.message, exception)
                }
                .collect {
                    Log.i(TAG, "ViewModel Collect")
                }
        }
    }

    fun hasAccessToken() {
        viewModelScope.launch {
            hasAccessTokenUseCase().catch { exception ->
                Log.e(
                    TAG,
                    exception.message,
                    exception
                )
            }.collect {
                _loginResult.emit(it)
            }
        }
    }

    fun getAccessTokenInfo(callback: (AccessTokenInfo?, Throwable?) -> Unit) {
        viewModelScope.launch {
            getAccessTokenInfoUseCase(callback).catch { exception ->
                Log.e(
                    TAG,
                    exception.message,
                    exception
                )
            }
        }
    }

    fun getMembership(callback: OnSuccessListener<DataSnapshot>) {
        viewModelScope.launch {
            getMembershipUseCase(callback).catch { Log.e(TAG, "Failed getMembership()", it) }
                .collect { Log.i(TAG, "getMembership success") }
        }
    }


    companion object {
        const val TAG = "LoginViewModel"
    }
}