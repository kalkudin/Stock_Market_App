package com.example.finalproject.presentation.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.presentation.extension.safeNavigateWithArguments
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment

    private lateinit var navController: NavController

    private val requestPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { it: Map<String, Boolean> ->
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermission()

        bottomNavigationSetUp()
    }

    private fun bottomNavigationSetUp() {
        with(binding) {
            navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
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

    override fun onStart() {
        super.onStart()
        handleIntent(intent)
        intent.extras?.clear()
        intent = Intent()
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
        intent.extras?.clear()
        setIntent(Intent())
    }

    private fun handleIntent(intent: Intent) {
        Log.d("messaging","handling intent")
        intent.extras?.let { bundle ->
            if (bundle.containsKey("symbol")) {
                val symbol = bundle.getString("symbol")
                symbol?.let { navigateToDetailsFragment(it) }
            } else {
                val navController = findNavController(R.id.fragment_container)
                navController.navigate(R.id.stockHomeFragment)
            }
        }
    }

    private fun navigateToDetailsFragment(symbol: String) {
        Log.d("messaging", "Trying to navigate to details page with symbol $symbol")
        val bundle = bundleOf("symbol" to symbol)
        val navController = findNavController(R.id.fragment_container)
        val currentDestinationId = navController.currentDestination?.id
        Log.d("messaging", "Current destination: $currentDestinationId")
        if (currentDestinationId == R.id.stockHomeFragment) {
            try {
                val didNavigate = navController.safeNavigateWithArguments(
                    R.id.action_stockHomeFragment_to_companyDetailsFragment,
                    bundle
                )
                Log.d("messaging", "Did navigate: $didNavigate")
            } catch (e: Exception) {
                Log.e("messaging", "Error navigating", e)
            }
        } else {
            Log.d("messaging", "Unexpected current destination")
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermission.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
        }
    }
}