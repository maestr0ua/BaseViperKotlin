package com.example.akarpenko.basekotlin.dagger.component

import com.example.akarpenko.basekotlin.dagger.component.scopes.DataProviderScope
import com.example.akarpenko.basekotlin.dagger.module.DataProviderModule
import com.example.akarpenko.basekotlin.domain.data_providers.BaseDataProvider
import dagger.Subcomponent

/**
 * Created by akarpenko on 27.10.16.
 */

@DataProviderScope
@Subcomponent(modules = arrayOf(DataProviderModule::class))
interface DataProviderComponent {

    fun inject(provider: BaseDataProvider)
}