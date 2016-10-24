package com.example.akarpenko.basekotlin.utils

import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject

class RxBus private constructor() {

    private val _bus = SerializedSubject(PublishSubject.create<Any>())

    fun send(o: Any) {
        _bus.onNext(o)
    }

    fun <T> connect(eventType: Class<T>): Observable<T> {
        return _bus.filter { o ->
            Logger.d("new event, class = " + eventType.name)
            o.javaClass == eventType
        }.cast(eventType)
    }

    companion object {
        @Volatile private var mDefaultInstance: RxBus? = null

        val instance: RxBus
            get() {
                if (mDefaultInstance == null) {
                    synchronized(RxBus::class.java) {
                        if (mDefaultInstance == null) {
                            mDefaultInstance = RxBus()
                        }
                    }
                }
                return mDefaultInstance!!
            }
    }

}
