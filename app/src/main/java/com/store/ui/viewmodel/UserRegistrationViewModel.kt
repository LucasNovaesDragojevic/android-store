package com.store.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.store.model.User
import com.store.repository.FirebaseAuthRepository
import com.store.repository.Resource

class UserRegistrationViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    fun register(user: User): LiveData<Resource<Boolean>> = firebaseAuthRepository.register(user)
}