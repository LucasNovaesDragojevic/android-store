package com.store.repository

import android.content.SharedPreferences
import androidx.core.content.edit

private const val KEY_LOGGED = "LOGGED"

class LoginRepository(private val preferences: SharedPreferences) {

    fun login() = preferences.edit { putBoolean(KEY_LOGGED, true) }
    fun logout() = preferences.edit { putBoolean(KEY_LOGGED, false) }
    fun isLogged(): Boolean = preferences.getBoolean(KEY_LOGGED,false)
    fun isNotLogged(): Boolean = !isLogged()
}
