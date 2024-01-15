package com.example.coupproject.data.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class CoupService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
//        stopService(Intent)
        Log.i("CoupService","End CoupService")
    }
}