package com.example.akarpenko.basekotlin.other.di.components

import com.example.akarpenko.basekotlin.dagger.component.ModelComponent
import com.example.akarpenko.basekotlin.dagger.component.scopes.ModelScope
import com.example.akarpenko.basekotlin.other.di.modules.MockModelModule
import dagger.Subcomponent

/**
 * Created by akarpenko on 27.10.16.
 */

@ModelScope
@Subcomponent(modules = arrayOf(MockModelModule::class))
interface TestModelComponent : ModelComponent {
}