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
import com.example.coupproject.domain.model.Photo
import com.example.coupproject.view.dialog.DialogActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class CoupService : Service() {
    private var token = ""
    private val addValueListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            Log.i(TAG, "onChildAdded()- ${snapshot.key}//${snapshot.value}")
            val photo = snapshot.getValue<Photo>()
            startActivity(
                Intent(
                    this@CoupService,
                    DialogActivity::class.java
                ).putExtra("token", photo?.token)
                    .putExtra(
                        "fileName",
                        photo?.fileName
                    )
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            Log.i(TAG, snapshot.key.toString() + "////")
//            startActivity(
//                Intent(
//                    this@CoupService,
//                    DialogActivity::class.java
//                )
//                    .putExtra(
//                        "fileName",
//                        snapshot.getValue<Photo>()?.fileName
//                    )
//                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            )
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            Log.i(TAG, "onChildRemoved()")
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            Log.i(TAG, "onChildMoved()")
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e(TAG, error.message, error.toException())
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        token = intent?.let { it.getStringExtra("token") }.toString()
        Firebase.database.reference.child(token).addChildEventListener(addValueListener)
        sendBroadcast(Intent("com.example.coup_project.START_COUP_SERVICE"))
        startForeground(1, notification.build())
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        Firebase.database.reference.child(token).removeEventListener(addValueListener)
        sendBroadcast(Intent("com.example.coup_project.END_COUP_SERVICE"))
        Log.i(TAG, "End CoupService")
    }

    companion object {
        const val TAG = "CoupService"
    }
}