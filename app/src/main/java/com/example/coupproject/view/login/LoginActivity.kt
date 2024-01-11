package com.example.coupproject.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

    companion object {
        const val TAG = "LoginActivity"
    }
}