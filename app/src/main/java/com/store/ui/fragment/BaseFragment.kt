package com.store.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.store.NavGraphDirections
import com.store.R
import com.store.ui.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModel()
    private val navController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        checkIfLogged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_main_logout) {
            loginViewModel.logout()
            goToLogin()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToLogin() {
        val direction = NavGraphDirections.actionGlobalLogin()
        navController.navigate(direction)
    }

    private fun checkIfLogged() {
        if (loginViewModel.isNotLogged())
            goToLogin()
    }
}