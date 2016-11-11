package com.example.akarpenko.basekotlin.other.di.components

import com.example.akarpenko.basekotlin.dagger.component.DataProviderComponent
import com.example.akarpenko.basekotlin.dagger.component.scopes.DataProviderScope
import com.example.akarpenko.basekotlin.other.di.modules.MockDataProviderModule
import dagger.Subcomponent

/**
 * Created by akarpenko on 27.10.16.
 */

@DataProviderScope
@Subcomponent(modules = arrayOf(MockDataProviderModule::class))
interface TestDataProviderComponent : DataProviderComponent {

}