package com.example.akarpenko.basekotlin

import android.content.Context
import com.example.akarpenko.basekotlin.dagger.component.AppComponent
import com.example.akarpenko.basekotlin.dagger.component.DataProviderComponent
import com.example.akarpenko.basekotlin.other.di.components.DaggerTestAppComponent
import com.example.akarpenko.basekotlin.other.di.components.TestAppComponent
import com.example.akarpenko.basekotlin.other.di.modules.MockAppModule
import com.example.akarpenko.basekotlin.other.di.modules.MockDataProviderModule

/**
 * Created by akarpenko on 08.11.16.
 */
class UnitTestApp : App() {

    override fun createComponent(): AppComponent {
        return DaggerTestAppComponent.builder()
                .mockAppModule(MockAppModule(this))
                .build()

    }

    override fun getProviderComponent(): DataProviderComponent {
        return (component as TestAppComponent).plus(MockDataProviderModule())
    }

    override fun attachBaseContext(base: Context) {
        try {
            super.attachBaseContext(base)
        } catch (ignored: RuntimeException) {
            // Multidex support doesn't play well with Robolectric yet
        }

    }

}