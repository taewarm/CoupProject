package com.example.coupproject.view.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.coupproject.R
import com.example.coupproject.databinding.ActivityLoginBinding
import com.example.coupproject.view.main.MainActivity
import com.example.coupproject.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.bind(layoutInflater.inflate(R.layout.activity_login, null))
        binding.activity = this
        requestPermission()
        setContentView(binding.root)
    }

    fun startKakaoLogin() {
        viewModel.startKakaoLogin(this) { token, error ->
            error?.let {
                Toast.makeText(this, "KakaoLogin Fail : ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
            token?.let {
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            } ?: Toast.makeText(this, "Error KakaoLogin", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            )
        ) {
            // 푸쉬 권한 없음
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}