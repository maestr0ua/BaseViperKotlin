package com.theappsolutions.tripperguru.data.model

import com.example.akarpenko.basekotlin.App
import com.example.akarpenko.basekotlin.dagger.module.ModelModule
import com.theappsolutions.tripperguru.general.Constants
import rx.Observable
import rx.Observable.Transformer
import rx.Scheduler
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by alkurop on 03.05.16.
 */

abstract class BaseModel {

    private val schedulersTransformer: Transformer<*, *>

    @Inject @field: [Named(Constants.UI_THREAD)]
    lateinit var uiThread: Scheduler

    @Inject @field: [Named(Constants.IO_THREAD)]
    lateinit var ioThread: Scheduler


    init {
        App.component.plus(ModelModule())
                .inject(this)

        schedulersTransformer = Transformer<Any, Any> {
            it.subscribeOn(ioThread)
                    .observeOn(uiThread)
        }

    }

    fun <T> Observable<T>.applySchedulers(): Observable<T> {
        return subscribeOn(ioThread)
                .observeOn(uiThread)
    }

}
