package com.example.akarpenko.basekotlin.other.di.components

import com.example.akarpenko.basekotlin.UnitTestApp
import com.example.akarpenko.basekotlin.dagger.component.AppComponent
import com.example.akarpenko.basekotlin.other.di.modules.MockAppModule
import com.example.akarpenko.basekotlin.other.di.modules.MockDataProviderModule
import com.example.akarpenko.basekotlin.other.di.modules.MockModelModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by akarpenko on 08.11.16.
 */

@Singleton
@Component(modules = arrayOf(MockAppModule::class))
interface TestAppComponent : AppComponent {

    fun plus(dataProviderModule: MockDataProviderModule): TestDataProviderComponent
    fun plus(modelModule: MockModelModule): TestModelComponent

    fun inject(app: UnitTestApp)

}