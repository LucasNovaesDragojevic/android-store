package com.store.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.store.model.User
import com.store.repository.FirebaseAuthRepository

class AccountViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    val user: LiveData<User> = firebaseAuthRepository.user()
}