package com.example.akarpenko.basekotlin.other.di.components

import com.example.akarpenko.basekotlin.dagger.component.PresenterComponent
import com.example.akarpenko.basekotlin.dagger.component.scopes.PresenterScope
import com.example.akarpenko.basekotlin.other.di.modules.MockPresenterModule
import dagger.Component

/**
 * Created by akarpenko on 28.10.16.
 */

@PresenterScope
@Component(modules = arrayOf(MockPresenterModule::class))
interface TestPresenterComponent : PresenterComponent {


}