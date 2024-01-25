package com.example.coupproject.view.member

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.coupproject.R
import com.example.coupproject.databinding.ActivityAddMemberBinding
import com.example.coupproject.view.login.LoginActivity
import com.example.coupproject.view.main.MainActivity
import com.example.coupproject.viewmodel.MemberViewModel
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMemberBinding
    private val viewModel: MemberViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemberBinding.bind(
            layoutInflater.inflate(
                R.layout.activity_add_member,
                null
            )
        )
        binding.btnOk.setOnClickListener {
            if (binding.edtName.text.isNotEmpty()) {
                me(binding.edtName.text.toString())
            }
        }
        setContentView(binding.root)
    }

    fun me(name: String) {
        UserApiClient.instance.me { user, error ->
            user?.let { users ->
                viewModel.setMembership(name, users.id.toString()) {
                    startActivity(
                        Intent(this@AddMemberActivity, MainActivity::class.java).putExtra(
                            "token",
                            users.id.toString()
                        )
                    )
                }
            }
            error?.let {
                Log.e(LoginActivity.TAG, it.message, it)
            }
        }
    }

    companion object {
        const val TAG = "AddMemberActivity"
    }
}