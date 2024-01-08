package com.example.coupproject.view.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.example.coupproject.R
import com.example.coupproject.databinding.ActivityLoginBinding
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ViewDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.bind(layoutInflater.inflate(R.layout.activity_login, null))
        (binding as ActivityLoginBinding).activity = this
        setContentView(binding.root)
    }

    fun startKakaoLogin() {
        if (!UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            Toast.makeText(this, "KakaoTalk not install", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
            error?.let {
                Log.e(TAG, "login Fail ${it.message}", it)
                return@loginWithKakaoTalk
            }
            token?.let {
                Log.i(TAG, "login success ${it.accessToken}")
//                val intent = Intent(context, MainActivity::class.java)
//                intent.putExtra("token", "${it.accessToken}")
//                context.startActivity(intent)
            }
            Log.i(TAG, "$token $error ///")
        }
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}