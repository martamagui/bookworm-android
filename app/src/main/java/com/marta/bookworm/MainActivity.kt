package com.marta.bookworm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.marta.bookworm.databinding.ActivityMainBinding
import com.marta.bookworm.ui.ApplicationActivity
import com.marta.bookworm.ui.login.LoginFragmentViewModel
import com.marta.bookworm.ui.login.LoginUIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: LoginFragmentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_BookWorm)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launchWhenCreated {
            viewModel.loginUIState.collect { loginUIState ->
                renderUIState(loginUIState)
            }
        }
    }

    private fun renderUIState(loginUIState: LoginUIState) {
        if (loginUIState.isSuccess) {
            Log.e("MAIN", "$loginUIState.savedToken")
            val intent = Intent(this, ApplicationActivity::class.java)
            intent.putExtra("token", loginUIState.savedToken)
            startActivity(intent)
        }
    }

}