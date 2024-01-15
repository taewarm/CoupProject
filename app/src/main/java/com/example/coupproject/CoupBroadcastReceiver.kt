package com.example.coupproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class CoupBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, intent: Intent?) {
        Log.i("CoupBroadcastReceiver", "성공")
        intent?.action?.let {
            Log.i("CoupBroadcastReceiver", "$it 액션")
        }
    }
}