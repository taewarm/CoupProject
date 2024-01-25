package com.example.coupproject.view.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.coupproject.R
import com.example.coupproject.databinding.ActivityLoginBinding
import com.example.coupproject.view.main.MainActivity
import com.example.coupproject.view.member.AddMemberActivity
import com.example.coupproject.viewmodel.LoginViewModel
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.bind(layoutInflater.inflate(R.layout.activity_login, null))
        binding.activity = this
        me()
        requestPermission()
        setContentView(binding.root)
    }

    fun logout() {
        UserApiClient.instance.logout {
            Log.e(TAG, "Kakao Logout")
        }
    }

    fun me() {
        UserApiClient.instance.me { user, error ->
            user?.let {
                viewModel.getMembership { snapshot ->
                    if (snapshot.hasChild(it.id.toString())) {
                        startActivity(
                            Intent(this@LoginActivity, MainActivity::class.java).putExtra(
                                "token",
                                it.id.toString()
                            )
                        )
                    }
                }
            }
            error?.let {
                Log.e(TAG, it.message, it)
            }
        }
    }

    fun startKakaoLogin() {
        viewModel.startKakaoLogin(this) { token, error ->
            error?.let {
                Toast.makeText(this, "KakaoLogin Fail : ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
            token?.let {
                finish()
                Log.i(TAG, "$it")
                startActivity(Intent(this, AddMemberActivity::class.java))
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
                NOTIFICATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            NOTIFICATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    } else {
                        Toast.makeText(
                            applicationContext,
                            "NOTIFICATION_PERMISSION_DENIED",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        finish()
                    }
                }
            }

            else -> ""
        }
    }

    private fun getAccessToken() {
        viewModel.getAccessTokenInfo { accessTokenInfo, throwable ->
            accessTokenInfo?.let {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        }
    }

    companion object {
        const val TAG = "LoginActivity"
        const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1995
    }
}