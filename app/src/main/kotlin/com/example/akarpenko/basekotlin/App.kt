package com.example.akarpenko.basekotlin

import android.app.Application
import com.example.akarpenko.basekotlin.utils.ForegroundManager

class App : Application(), ForegroundManager.Listener {

    companion object {
        lateinit var instance: App
    }

    var isForeground = false
    private lateinit var foregroundManager: ForegroundManager

    override fun onCreate() {
        super.onCreate()
        instance = this

        foregroundManager = ForegroundManager.init(this)!!
        foregroundManager.addListener(this)
    }

    override fun onBecameForeground() {
        isForeground = true
    }

    override fun onBecameBackground() {
        isForeground = false
    }

}
