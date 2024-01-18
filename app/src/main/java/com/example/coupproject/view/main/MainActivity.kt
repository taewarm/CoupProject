package com.example.coupproject.view.main

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.viewModelScope
import com.example.coupproject.R
import com.example.coupproject.data.service.CoupService
import com.example.coupproject.databinding.ActivityMainBinding
import com.example.coupproject.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var count = 0
    private val br = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.i("Coupproject", "${p1?.action} ////")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.bind(layoutInflater.inflate(R.layout.activity_main, null))
        viewModel.viewModelScope.launch {
            viewModel.friend.collect { friend ->
                binding.btnAddFriends.isVisible = (friend.name.isEmpty() || friend.id.isEmpty())
            }
        }
        binding.btnAddFriends.setOnClickListener {
//            val intent = Intent()
//            intent.action = "coupproject.action.START_SERVICE"
//            sendBroadcast(intent)
            Log.i(MainActivity::class.java.name, "${isMyServiceRunning(CoupService::class.java)}")
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startService(Intent(applicationContext, CoupService::class.java))
                } else {
                    startService(Intent(applicationContext, CoupService::class.java))
                }
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startService(Intent(applicationContext, CoupService::class.java))
            } else {
                startService(Intent(applicationContext, CoupService::class.java))
            }
        }
    }
    companion object{

    }
}