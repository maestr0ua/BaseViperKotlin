package com.example.akarpenko.basekotlin.dagger.component

import com.example.akarpenko.basekotlin.App
import com.example.akarpenko.basekotlin.dagger.module.AppModule
import com.example.akarpenko.basekotlin.dagger.module.DataProviderModule
import com.example.akarpenko.basekotlin.dagger.module.ModelModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by akarpenko on 27.10.16.
 */

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun plus(dataProviderModule: DataProviderModule): DataProviderComponent
    fun plus(modelModule: ModelModule): ModelComponent

    fun inject(app: App)
}