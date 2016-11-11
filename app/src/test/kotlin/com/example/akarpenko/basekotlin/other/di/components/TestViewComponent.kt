package com.example.akarpenko.basekotlin.other.di.components

import com.example.akarpenko.basekotlin.dagger.component.ViewComponent
import com.example.akarpenko.basekotlin.dagger.component.scopes.ViewScope
import com.example.akarpenko.basekotlin.other.di.modules.MockViewModule
import dagger.Component

/**
 * Created by akarpenko on 28.10.16.
 */

@ViewScope
@Component(modules = arrayOf(MockViewModule::class))
interface TestViewComponent : ViewComponent {

}
