package com.example.akarpenko.basekotlin.dagger.module

import com.example.akarpenko.basekotlin.MainPresenter
import com.example.akarpenko.basekotlin.TestPresenter
import com.example.akarpenko.basekotlin.dagger.component.scopes.ViewScope
import com.example.akarpenko.basekotlin.dialogs.ConfirmPresenter
import com.example.akarpenko.basekotlin.dialogs.InfoPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by alkurop on 03.05.16.
 */
@Module
class ViewModule() {

    @ViewScope
    @Provides fun provideMainPresenter(): MainPresenter = MainPresenter()

    @ViewScope
    @Provides fun provideTestPresenter(): TestPresenter = TestPresenter()

    @ViewScope
    @Provides fun provideConfirmPresenter(): ConfirmPresenter = ConfirmPresenter()

    @ViewScope
    @Provides fun provideInfoPresenter(): InfoPresenter = InfoPresenter()


}




