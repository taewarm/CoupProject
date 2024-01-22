package com.example.coupproject.view.main

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.viewModelScope
import com.example.coupproject.BuildConfig
import com.example.coupproject.R
import com.example.coupproject.data.service.CoupService
import com.example.coupproject.databinding.ActivityMainBinding
import com.example.coupproject.domain.model.Photo
import com.example.coupproject.viewmodel.MainViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

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
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.bind(layoutInflater.inflate(R.layout.activity_main, null))
        binding.activity = this
        viewModel.viewModelScope.launch {
            viewModel.friend.collect { friend ->
                binding.btnAddFriends.isVisible = (friend.name.isEmpty() || friend.id.isEmpty())
            }
        }
        binding.btnAddFriends.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }
            activityResultLauncher.launch(intent)
//            viewModel.getFriend()
        }
        setContentView(binding.root)
    }

    fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
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
            } else {
                startService(Intent(this, CoupService::class.java))
            }
        } else {
            startService(Intent(this, CoupService::class.java))
        }
    }

    fun startService() {
        if (isMyServiceRunning(CoupService::class.java)) {
            Log.i(TAG, "StopService - CoupService")
            stopService(Intent(this, CoupService::class.java))
        } else {
            Log.i(TAG, "StartService - CoupService")
            startService(Intent(this, CoupService::class.java))
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
                Firebase.database.reference.child("users").setValue(Photo("taetaewon1"))
            }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}