package com.example.bankingapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bankingapp.data.TokenViewModel
import com.example.bankingapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tokenViewModel: TokenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


        // Grab NavController safely from FragmentContainerView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        // Set top-level destinations for the AppBar
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_cards,
                R.id.navigation_transactions,
                R.id.navigation_profile
            )
        )


        tokenViewModel.token.observe(this) { token ->
            if (token == null){
                navController.navigate(R.id.navigation_login)
            }
        }


        // Hide bottom nav on login/register screens
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.navView.visibility = if (
                destination.id == R.id.navigation_login ||
                destination.id == R.id.navigation_register
            ) {
                View.GONE

            }
            else {
                View.VISIBLE
            }
        }


        navView.setupWithNavController(navController)
    }





}