package com.example.akarpenko.basekotlin.dagger.component

import com.example.akarpenko.basekotlin.dagger.component.scopes.ModelScope
import com.example.akarpenko.basekotlin.dagger.module.ModelModule
import com.theappsolutions.tripperguru.data.model.BaseModel
import dagger.Subcomponent

/**
 * Created by akarpenko on 27.10.16.
 */

@ModelScope
@Subcomponent(modules = arrayOf(ModelModule::class))
interface ModelComponent {

    fun inject(model: BaseModel)
}