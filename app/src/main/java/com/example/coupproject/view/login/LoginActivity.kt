package com.example.coupproject.view.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.example.coupproject.R
import com.example.coupproject.databinding.ActivityLoginBinding
import com.example.coupproject.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ViewDataBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.bind(layoutInflater.inflate(R.layout.activity_login, null))
        (binding as ActivityLoginBinding).activity = this
        setContentView(binding.root)
    }

    fun startKakaoLogin() {
        viewModel.startKakaoLogin()
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}