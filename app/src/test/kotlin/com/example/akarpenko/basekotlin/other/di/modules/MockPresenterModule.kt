package com.example.akarpenko.basekotlin.other.di.modules

import com.example.akarpenko.basekotlin.dagger.component.scopes.PresenterScope
import com.example.akarpenko.basekotlin.domain.model.MainModel
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock

/**
 * Created by alkurop on 05.05.16.
 */
@Module
class MockPresenterModule() {

    @PresenterScope
    @Provides fun provideMainModel() = mock(MainModel::class.java)

}