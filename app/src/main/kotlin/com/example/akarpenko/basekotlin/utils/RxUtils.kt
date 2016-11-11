package com.example.akarpenko.basekotlin.utils

import android.view.View
import com.jakewharton.rxbinding.view.RxView
import rx.Observable
import rx.Subscription
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


    fun click(view: View, action: ((Any?) -> Unit)?) {
        RxView.clicks(view)
                .throttleFirst(800, TimeUnit.MILLISECONDS)
                .subscribe(action, { Logger.e(it) })
    }

    fun <T> emptyListObservable(): Observable<List<T>> {
        return Observable.create { subscriber ->
            subscriber.onNext(ArrayList<T>())
            subscriber.onCompleted()
        }
    }

}
