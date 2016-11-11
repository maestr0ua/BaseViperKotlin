package com.example.akarpenko.basekotlin.dagger.module

import android.content.Context
import com.example.akarpenko.basekotlin.BuildConfig
import com.example.akarpenko.basekotlin.dagger.component.scopes.DataProviderScope
import com.example.akarpenko.basekotlin.domain.repositories.network.Api
import com.example.akarpenko.basekotlin.domain.repositories.network.RetrofitAdapter
import com.example.akarpenko.basekotlin.domain.repositories.preference.IPreferences
import com.example.akarpenko.basekotlin.domain.repositories.preference.Preferences
import dagger.Module
import dagger.Provides

/**
 * Created by akarpenko on 27.10.16.
 */

@Module
class DataProviderModule(val context: Context) {

    @Provides
    @DataProviderScope
    fun provideRestApi(): Api {
        return RetrofitAdapter().createApi(Api::class.java, BuildConfig.SERVER_ENDPOINT)
    }

    @Provides
    @DataProviderScope
    fun providePreferenceHelper(): IPreferences = Preferences

}
