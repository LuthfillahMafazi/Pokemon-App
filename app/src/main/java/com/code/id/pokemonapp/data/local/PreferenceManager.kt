package com.code.id.pokemonapp.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import androidx.core.content.edit

class PreferenceManager @Inject constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)

    fun saveUserLogin(userId: Int, username: String) {
        sharedPreferences.edit {
            putBoolean(KEY_IS_LOGGED_IN, true)
                .putInt(KEY_USER_ID, userId)
                .putString(KEY_USERNAME, username)
        }
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUserId(): Int = sharedPreferences.getInt(KEY_USER_ID, -1)

    fun getUsername(): String? = sharedPreferences.getString(KEY_USERNAME, null)

    fun logout() {
        sharedPreferences.edit { clear() }
    }

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USERNAME = "username"
        private const val KEY_USER_ID = "user_id"
    }

}