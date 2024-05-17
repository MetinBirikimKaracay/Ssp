package com.example.ssp

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.ssp.common.messageExtra
import com.example.ssp.common.navigation.NavigationData
import com.example.ssp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var navHostFragment: NavHostFragment

    private val navController by lazy(LazyThreadSafetyMode.NONE) {
        (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainViewModel.navigateToDestinationComponentSingleLiveEvent.observe(this@MainActivity) {
            navigateToDestination(navController = navController, navigationData = it)
        }

        navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    }
    private fun navigateToDestination(navController: NavController, navigationData: NavigationData) {
        navController.navigate(navigationData.destinationId)
    }

    private fun showNotificationDialog() {
        val dialogBuilder = AlertDialog.Builder(applicationContext)
        dialogBuilder.setMessage(intent.getStringExtra(messageExtra))
            .setCancelable(false)
            .setPositiveButton("Tamam") { dialog, _ ->
                dialog.dismiss()
            }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}