package com.example.coupproject.view

import android.app.Application
import android.util.Log
import com.example.coupproject.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoupApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
        Log.i("CoupApplication", Utility.getKeyHash(this))
    }
}