package com.example.akarpenko.basekotlin.utils

import android.view.View
import com.jakewharton.rxbinding.view.RxView
import rx.Observable
import rx.Subscription
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by klim on 21.10.15.
 */
object RxUtils {

    fun unsubscribeIfNotNull(subscription: Subscription?) {
        subscription?.unsubscribe()
    }

    fun getNewCompositeSubIfUnsubscribed(subscription: CompositeSubscription?): CompositeSubscription {
        if (subscription == null || subscription.isUnsubscribed) {
            return CompositeSubscription()
        }

        return subscription
    }


    fun click(view: View, action: Action1<in Any>) {
        RxView.clicks(view)
                .throttleFirst(800, TimeUnit.MILLISECONDS)
                .subscribe(action, Action1<Throwable> { Logger.e(it) })
    }


    fun click(_view: View): Observable<Any> {
        return RxView.clicks(_view).throttleFirst(800, TimeUnit.MILLISECONDS)
    }

    fun <T> emptyListObservable(type: Class<T>): Observable<List<T>> {
        return Observable.create { subscriber ->
            subscriber.onNext(ArrayList<T>())
            subscriber.onCompleted()
        }
    }

}
