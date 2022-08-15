package com.store.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.store.R
import com.store.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.activity_main_nav_host) }

    private val viewModel: MainViewModel by viewModel()

    private val bottomNavigationView by lazy {
        findViewById<BottomNavigationView>(R.id.activity_main_bottom_navigation)
    }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                title = destination.label
                viewModel.compnents.observe(this) {
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