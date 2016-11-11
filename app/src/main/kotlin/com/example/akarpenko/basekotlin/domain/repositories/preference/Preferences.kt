package com.example.akarpenko.basekotlin.domain.repositories.preference

import android.content.Context
import android.content.SharedPreferences
import com.example.akarpenko.basekotlin.App

private val PREF = "basePreferences"
private val PREF_PERMISSION_GRANTED = "PREF_PERMISSION_GRANTED"

interface IPreferences {
    fun retrievePermissionGranted(): Boolean
    fun storePermissionGranted(granted: Boolean)
}

object Preferences : IPreferences {
    var preferences: SharedPreferences

    init {
        preferences = App.instance.getSharedPreferences(PREF, Context.MODE_PRIVATE)
    }

    private fun retrieveString(key: String): String = preferences.getString(key, "")
    private fun retrieveBoolean(key: String): Boolean = preferences.getBoolean(key, false)
    private fun retrieveInt(key: String): Int = preferences.getInt(key, -1)
    private fun retrieveLong(key: String): Long = preferences.getLong(key, -1)

    private fun storeString(key: String, value: String) = preferences.edit().putString(key, value).apply()
    private fun storeBoolean(key: String, value: Boolean) = preferences.edit().putBoolean(key, value).apply()
    private fun storeInt(key: String, value: Int) = preferences.edit().putInt(key, value).apply()
    private fun storeLong(key: String, value: Long) = preferences.edit().putLong(key, value).apply()


    override fun retrievePermissionGranted(): Boolean {
        return retrieveBoolean(PREF_PERMISSION_GRANTED)
    }

    override fun storePermissionGranted(granted: Boolean) {
        storeBoolean(PREF_PERMISSION_GRANTED, granted)
    }

}