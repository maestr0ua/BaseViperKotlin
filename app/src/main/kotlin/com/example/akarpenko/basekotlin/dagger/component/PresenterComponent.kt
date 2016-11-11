package com.example.akarpenko.basekotlin.dagger.component

import com.example.akarpenko.basekotlin.MainPresenter
import com.example.akarpenko.basekotlin.TestPresenter
import com.example.akarpenko.basekotlin.dagger.component.scopes.PresenterScope
import com.example.akarpenko.basekotlin.dagger.module.PresenterModule
import dagger.Component

/**
 * Created by akarpenko on 28.10.16.
 */

@PresenterScope
@Component(modules = arrayOf(PresenterModule::class))
interface PresenterComponent {

    fun inject(mainPresenter: MainPresenter)
    fun inject(testPresenter: TestPresenter)

}