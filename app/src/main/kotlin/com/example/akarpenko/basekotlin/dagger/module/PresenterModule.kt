package com.example.akarpenko.basekotlin.dagger.module

import com.example.akarpenko.basekotlin.dagger.component.scopes.PresenterScope
import com.example.akarpenko.basekotlin.domain.model.MainModel
import dagger.Module
import dagger.Provides

/**
 * Created by alkurop on 05.05.16.
 */
@Module
class PresenterModule() {

    @PresenterScope
    @Provides fun provideMainModel(): MainModel = MainModel()

}