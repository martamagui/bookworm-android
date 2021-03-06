package com.marta.bookworm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.FirebaseApp
import com.marta.bookworm.R
import com.marta.bookworm.databinding.ActivityApplicationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplicationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_BookWorm)
        super.onCreate(savedInstanceState)
        binding = ActivityApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigation()
        changeStatusBarColor()
        FirebaseApp.initializeApp(this)
    }
    private fun setNavigation(){
        val navigationMenu =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        val navigationController = navigationMenu.navController
        binding.bottomNavigationView.setupWithNavController(navigationController)
    }

    private fun changeStatusBarColor() {
        getWindow()?.setStatusBarColor(getColor(R.color.outline))
    }
}