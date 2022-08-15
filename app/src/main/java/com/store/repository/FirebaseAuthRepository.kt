package com.store.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.*
import com.store.model.User

private const val TAG = "FirebaseAuthRepository"

class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth
) {
    fun register (user: User): LiveData<Resource<Boolean>> {
        val liveData = MutableLiveData<Resource<Boolean>>()
        try {
            firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnSuccessListener {
                    Log.i(TAG, "User registered")
                    liveData.value = Resource(true)
                }
                .addOnFailureListener { exception ->
                    Log.i(TAG, "Fail on register", exception)
                    val errorMessage = catchFirebaseAuthErrors(exception)
                    liveData.value = Resource(false, errorMessage)
                }
        } catch (e: IllegalArgumentException) {
            liveData.value = Resource(false, "E-mail and Password cannot be null")
        }

        return liveData
    }

    fun isLogged(): Boolean {
        if (firebaseAuth.currentUser != null)
            return true
        return false
    }

    fun logout() = firebaseAuth.signOut()

    fun login(user: User): LiveData<Resource<Boolean>> {
        val liveData = MutableLiveData<Resource<Boolean>>()
        try {
            firebaseAuth.signInWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful) {
                        liveData.value = Resource(true)
                    } else {
                        Log.e("login", authResult.exception.toString())
                        val errorMessage = getAuthError(authResult.exception)
                        liveData.value = Resource(false, errorMessage)
                    }
                }
        } catch (e: IllegalArgumentException) {
            liveData.value = Resource(false, "E-mail or password cannot be null")
        }

        return liveData
    }

    fun user(): LiveData<User> {
        val liveData = MutableLiveData<User>()
        firebaseAuth.currentUser?.let { firebaseUser ->
            firebaseUser.email?.let {
                liveData.value = User(it, "")
            }
        }
        return liveData
    }

    private fun getAuthError(exception: Exception?) =
        when (exception) {
            is FirebaseAuthInvalidUserException,
            is FirebaseAuthInvalidCredentialsException -> "E-mail or password is invalid"
            else -> "Unknown error"
        }

    private fun catchFirebaseAuthErrors(exception: Exception): String =
        when (exception) {
            is FirebaseAuthWeakPasswordException -> "Password needs at least 6 characters"
            is FirebaseAuthInvalidCredentialsException -> "Invalid e-mail"
            is FirebaseAuthUserCollisionException -> "User already registered"
            else -> "Unknown error"
        }
}

