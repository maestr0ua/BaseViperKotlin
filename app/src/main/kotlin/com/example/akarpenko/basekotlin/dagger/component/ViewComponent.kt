package com.example.akarpenko.basekotlin.dagger.component

import com.example.akarpenko.basekotlin.MainActivity
import com.example.akarpenko.basekotlin.TestFragment
import com.example.akarpenko.basekotlin.dagger.component.scopes.ViewScope
import com.example.akarpenko.basekotlin.dagger.module.ViewModule
import com.example.akarpenko.basekotlin.dialogs.ConfirmDialog
import com.example.akarpenko.basekotlin.dialogs.InfoDialog
import dagger.Component

/**
 * Created by akarpenko on 28.10.16.
 */

@ViewScope
@Component(modules = arrayOf(ViewModule::class))
interface ViewComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(testFragment: TestFragment)


    fun inject(confirmDialog: ConfirmDialog)
    fun inject(infoDialog: InfoDialog)

}
