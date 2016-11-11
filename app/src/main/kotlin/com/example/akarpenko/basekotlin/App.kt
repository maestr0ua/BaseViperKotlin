package com.example.akarpenko.basekotlin

import android.app.Application
import com.example.akarpenko.basekotlin.utils.ForegroundManager
import com.example.akarpenko.basekotlin.dagger.component.AppComponent
import com.example.akarpenko.basekotlin.dagger.component.DaggerAppComponent
import com.example.akarpenko.basekotlin.dagger.component.DataProviderComponent
import com.example.akarpenko.basekotlin.dagger.module.AppModule
import com.example.akarpenko.basekotlin.dagger.module.DataProviderModule

open class App : Application(), ForegroundManager.Listener {

    companion object {
        lateinit var instance: App
        lateinit var component: AppComponent
    }

    var isForeground = false
    private lateinit var foregroundManager: ForegroundManager

    override fun onCreate() {
        super.onCreate()
        instance = this

        component = createComponent()
        component.inject(this)

        foregroundManager = ForegroundManager.init(this)!!
        foregroundManager.addListener(this)
    }

    open fun createComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()!!
    }


    open fun getProviderComponent(): DataProviderComponent {
        return component.plus(DataProviderModule(this))
    }


    override fun onBecameForeground() {
        isForeground = true
    }

    override fun onBecameBackground() {
        isForeground = false
    }

    fun isDebuggable(): Boolean {
        return BuildConfig.DEBUG
    }

}
