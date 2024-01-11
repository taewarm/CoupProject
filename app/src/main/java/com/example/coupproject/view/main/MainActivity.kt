package com.example.coupproject.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.viewModelScope
import com.example.coupproject.R
import com.example.coupproject.databinding.ActivityMainBinding
import com.example.coupproject.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.bind(layoutInflater.inflate(R.layout.activity_main, null))
        viewModel.viewModelScope.launch {
            viewModel.friend.collect { friend ->
                binding.btnAddFriends.isVisible = (friend.name.isEmpty() || friend.id.isEmpty())
            }
        }
        setContentView(binding.root)
    }
}