package com.store.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.store.repository.LoginRepository

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    fun login() = repository.login()
    fun logout() = repository.logout()
    fun isLogged() = repository.isLogged()
    fun isNotLogged() = repository.isNotLogged()
}
