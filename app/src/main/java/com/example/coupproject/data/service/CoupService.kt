package com.example.coupproject.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.coupproject.R

class CoupService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("CoupService", "Start")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i("CoupService", "Version Oreo <<<")
            val name = "name"//getString(R.string.channel_name)
            val descriptionText = "descriptionText"//getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("ServiceStart", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(this, "ServiceStart")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("CoupService")
            .setContentText("Play CoupService..")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        startForeground(1, notification.build())
        Log.i("CoupService", "End")
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
//        stopService(Intent)
        Log.i("CoupService", "End CoupService")
    }
}