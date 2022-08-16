package com.store.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.store.model.User
import com.store.repository.FirebaseAuthRepository

class LoginViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    fun login(user: User) = firebaseAuthRepository.login(user)
    fun logout() = firebaseAuthRepository.logout()
    fun isLogged() = firebaseAuthRepository.isLogged()
    fun isNotLogged() = !isLogged()
}
