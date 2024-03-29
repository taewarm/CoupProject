package com.example.coupproject.view.main

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.example.coupproject.BuildConfig
import com.example.coupproject.R
import com.example.coupproject.data.service.CoupService
import com.example.coupproject.databinding.ActivityMainBinding
import com.example.coupproject.domain.model.Photo
import com.example.coupproject.domain.model.User
import com.example.coupproject.viewmodel.MainViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.i(TAG, "성공 ${it.data?.data}")
            it.data?.data?.let { photoUri ->
                photoUpload(photoUri)
            }
        }
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                "com.example.coup_project.START_COUP_SERVICE" -> binding.btnStartService.text =
                    "서비스 중지"

                "com.example.coup_project.END_COUP_SERVICE" -> binding.btnStartService.text =
                    "서비스 시작"
            }
        }
    }
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.bind(layoutInflater.inflate(R.layout.activity_main, null))
        binding.activity = this
        viewModel.getFriend(intent.getStringExtra("token").toString()) {
            val member = it.getValue<User>()
            member?.let { user ->
                viewModel.saveFriend(user)
            }
        }
        if (isMyServiceRunning(CoupService::class.java)) binding.btnStartService.text =
            "서비스 중지" else binding.btnStartService.text = "서비스 시작"
        viewModel.viewModelScope.launch {
            viewModel.hasMembership.collect { user ->
                binding.btnAddFriends.visibility =
                    if (user.friend?.isNotEmpty() == true) View.GONE else View.VISIBLE
            }
        }
        binding.btnAddFriends.setOnClickListener {

        }
        val intentFilter = IntentFilter().apply {
            addAction("com.example.coup_project.START_COUP_SERVICE")
            addAction("com.example.coup_project.END_COUP_SERVICE")
        }
        registerReceiver(broadcastReceiver, intentFilter)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        try {
            val manager =
                getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(
                Int.MAX_VALUE
            )) {
                if (serviceClass.name == service.service.className) {
                    return true
                }
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                if (count > 0) {
                    Toast.makeText(this, "다른 앱 위에 표시 권한을 체크해주세요.", Toast.LENGTH_SHORT).show()
                    finish()
                    return
                }
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
                count += 1
            }
//            else {
//                startService(Intent(this, CoupService::class.java))
//            }
        }
//        else {
//            startService(Intent(this, CoupService::class.java))
//        }
    }

    fun startService() {
        val token = intent.getStringExtra("token").toString()
        if (token.isNotEmpty()) {
            if (isMyServiceRunning(CoupService::class.java)) {
                Log.i(TAG, "StopService - CoupService")
                stopService(Intent(this, CoupService::class.java).putExtra("token", token))
            } else {
                Log.i(TAG, "StartService - CoupService")
                startService(Intent(this, CoupService::class.java).putExtra("token", token))
            }
        }
    }

    private fun photoUpload(uri: Uri) {
        val storageRef = FirebaseStorage.getInstance(BuildConfig.FIREBASE_URI_KEY).reference
        val sdf = SimpleDateFormat("yyyyMMddhhmmss")
        val fileName = "taetaewon1"//sdf.format(Date()) + ".jpg"
        val riversRef = storageRef.child("images/$fileName")

        riversRef.putFile(uri).addOnProgressListener { taskSnapshot ->
            val btf = taskSnapshot.bytesTransferred
            val tbc = taskSnapshot.totalByteCount
            val progress: Double = 100.0 * btf / tbc
            Log.i(TAG, "progress : $progress")
        }.addOnFailureListener { Log.e(TAG, "Fail $fileName Upload") }
            .addOnSuccessListener {
                Log.i(TAG, "Success $fileName Upload")
                viewModel.hasMembership.value.friend?.let {
                    Log.i(TAG, "$it let 진")
                    Firebase.database.reference.child(it)
                        .child("photo")
                        .setValue(
                            Photo(it, "taetaewon1"),
                            ""
                        )
                }
            }
        Log.i(TAG, "End $fileName Upload")
    }

    fun selectPhoto() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }
        activityResultLauncher.launch(intent)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}