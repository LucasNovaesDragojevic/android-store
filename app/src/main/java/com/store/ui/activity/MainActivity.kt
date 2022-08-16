package com.store.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.store.R
import com.store.model.Product
import com.store.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal

private const val TAG = "MainActivityTAG"

class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.activity_main_nav_host) }

    private val mainViewModel: MainViewModel by viewModel()

    private val bottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.activity_main_bottom_navigation) }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                title = destination.label
                mainViewModel.compnents.observe(this) {
                    it.let { hasComponents ->
                        if (hasComponents.appBar) {
                            supportActionBar?.show()
                        } else {
                            supportActionBar?.hide()
                        }
                        if (hasComponents.bottomNavigation) {
                            bottomNavigationView.visibility = VISIBLE
                        } else {
                            bottomNavigationView.visibility = GONE
                    }
                }
            }
            bottomNavigationView.setupWithNavController(navController)
        }
    }
}