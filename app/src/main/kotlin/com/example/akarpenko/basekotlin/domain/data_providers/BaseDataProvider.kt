package com.example.akarpenko.basekotlin.domain.data_providers

import com.example.akarpenko.basekotlin.App
import com.example.akarpenko.basekotlin.domain.repositories.network.Api
import com.example.akarpenko.basekotlin.domain.repositories.preference.IPreferences
import javax.inject.Inject

/**
 * Created by akarpenko on 27.10.16.
 */
abstract class BaseDataProvider() {

    init {
        App.instance.getProviderComponent()
                .inject(this)
    }

    @Inject
    lateinit var apiHelper: Api

    @Inject
    lateinit var preferenceHelper: IPreferences


}