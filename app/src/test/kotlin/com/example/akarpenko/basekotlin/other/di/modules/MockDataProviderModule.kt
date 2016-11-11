package com.example.akarpenko.basekotlin.other.di.modules

import com.example.akarpenko.basekotlin.dagger.component.scopes.DataProviderScope
import com.example.akarpenko.basekotlin.domain.repositories.db.DBHelper
import com.example.akarpenko.basekotlin.domain.repositories.network.Api
import com.example.akarpenko.basekotlin.domain.repositories.preference.IPreferences
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock

/**
 * Created by akarpenko on 27.10.16.
 */

@Module
class MockDataProviderModule() {

    @Provides
    @DataProviderScope
    fun provideRestApi(): Api = mock(Api::class.java)

    @Provides
    @DataProviderScope
    fun provideDBHelper(): DBHelper = mock(DBHelper::class.java)

    @Provides
    @DataProviderScope
    fun providePreferenceHelper(): IPreferences = mock(IPreferences::class.java)

}
