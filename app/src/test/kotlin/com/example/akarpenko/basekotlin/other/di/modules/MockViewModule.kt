package com.example.akarpenko.basekotlin.other.di.modules

import com.example.akarpenko.basekotlin.MainPresenter
import com.example.akarpenko.basekotlin.TestPresenter
import com.example.akarpenko.basekotlin.dagger.component.scopes.ViewScope
import com.example.akarpenko.basekotlin.dialogs.ConfirmPresenter
import com.example.akarpenko.basekotlin.dialogs.InfoPresenter
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock

/**
 * Created by alkurop on 03.05.16.
 */
@Module
class MockViewModule() {

    @ViewScope
    @Provides fun provideMainPresenter() = mock(MainPresenter::class.java)

    @ViewScope
    @Provides fun provideTestPresenter() = mock(TestPresenter::class.java)

    @ViewScope
    @Provides fun provideConfirmPresenter() = mock(ConfirmPresenter::class.java)

    @ViewScope
    @Provides fun provideInfoPresenter() = mock(InfoPresenter::class.java)

}




