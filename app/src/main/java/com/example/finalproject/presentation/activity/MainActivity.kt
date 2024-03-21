package com.example.finalproject.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationSetUp()
    }

    private fun bottomNavigationSetUp() {
        with(binding) {
            navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
            navController = navHostFragment.findNavController()

            navView.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.welcomeFragment, R.id.forgotPasswordFragment, R.id.loginFragment, R.id.registerFragment, R.id.sessionFragment -> {
                        navView.visibility = View.GONE
                    }

                    else -> {
                        navView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}