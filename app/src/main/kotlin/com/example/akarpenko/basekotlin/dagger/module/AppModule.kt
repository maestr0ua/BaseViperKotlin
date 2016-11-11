package com.example.akarpenko.basekotlin.dagger.module

import android.content.Context
import com.theappsolutions.tripperguru.general.Constants
import dagger.Module
import dagger.Provides
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by akarpenko on 27.10.16.
 */

@Module
class AppModule(val context: Context) {

    @Provides
    @Singleton
    @Named(Constants.UI_THREAD)
    fun provideSchedulerUI(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @Singleton
    @Named(Constants.IO_THREAD)
    fun provideSchedulerIO(): Scheduler {
        return Schedulers.io()
    }

}
