package com.example.akarpenko.basekotlin.base

import android.content.Intent
import android.os.Bundle
import com.example.akarpenko.basekotlin.utils.ErrorHandler
import com.example.akarpenko.basekotlin.utils.Logger
import com.example.akarpenko.basekotlin.utils.RxBus
import com.example.akarpenko.basekotlin.utils.RxUtils
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action0
import rx.functions.Action1
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription


abstract class BasePresenterImpl(open val view: BaseView): BasePresenter {

    protected var mSubscriptions: CompositeSubscription? = null

    lateinit var router: Router

    override fun onCreateView(savedInstanceState: Bundle?) {

    }

    override fun onViewCreated() {
    }

    override fun onSaveInstanceState(outState: Bundle) {

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onDestroyView() {
        RxUtils.unsubscribeIfNotNull(mSubscriptions)
    }

    protected fun <T> executeWithoutProgress(request: Observable<T>, onSuccess: Action1<T>, onError: Action1<Throwable>): Subscription {
        return executeWithoutProgress(request, onSuccess, onError, null)
    }

    protected fun <T> executeWithoutProgress(request: Observable<T>, onSuccess: Action1<T>): Subscription {
        return executeWithoutProgress(request, onSuccess, null, null)
    }

    protected fun <T> executeWithoutProgress(request: Observable<T>): Subscription {
        return executeWithoutProgress(request, null, null, null)
    }


    protected fun <T> executeWithoutProgress(request: Observable<T>, onSuccess: Action1<T>?, onError: Action1<Throwable>?, onComplete: Action0?): Subscription {
        val subscription = request.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe({ t -> onExecuteSuccess(t, onSuccess) },
                { throwable -> onExecutingError(throwable, onError) }
        ) { onComplete?.call() }
        addSubscription(subscription)
        return subscription
    }

    protected fun <T> execute(request: Observable<T>, onSuccess: Action1<T>?, onError: Action1<Throwable>?, onComplete: Action0?): Subscription {
        if (onComplete == null) router.showLoadingDialog()

        val subscription = request
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { t -> onExecuteSuccess(t, onSuccess) },
                        { throwable -> onExecutingError(throwable, onError) },
                        { onExecutingComplete(onComplete) })

        addSubscription(subscription)
        return subscription
    }

    protected fun <T> execute(request: Observable<T>, onSuccess: Action1<T>, onError: Action1<Throwable>): Subscription {
        return execute(request, onSuccess, onError, null)
    }

    protected fun <T> execute(request: Observable<T>, onSuccess: Action1<T>): Subscription {
        return execute(request, onSuccess, null, null)
    }

    protected fun <T> execute(request: Observable<T>): Subscription {
        return execute(request, null, null, null)
    }


    private fun <T> onExecuteSuccess(t: T, onSuccess: Action1<T>?) {
        onSuccess?.call(t)
    }

    protected fun onExecutingError(throwable: Throwable, errorCallBack: Action1<Throwable>?) {

        if (errorCallBack != null) {
            Logger.e(throwable)
            errorCallBack.call(throwable)
        } else {
            ErrorHandler.onError(router, throwable)
        }
    }

    private fun onExecutingComplete(onComplete: Action0?) {
        if (onComplete == null) {
            router.hideLoadingDialog()
        } else {
            onComplete.call()
        }
    }

    protected fun <T> connectToBus(eventClass: Class<T>, onPublish: Action1<T>) {
        addSubscription(RxBus.instance.connect(eventClass)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onPublish))
    }

    protected fun sendByBus(event: Any) {
        RxBus.instance.send(event)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    }

    protected fun addSubscription(subscription: Subscription) {
        if (mSubscriptions == null)
            mSubscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(mSubscriptions)
        mSubscriptions!!.remove(subscription)
        mSubscriptions!!.add(subscription)
    }

    protected val arguments: Bundle
        get() = view.viewArguments

    override fun onBackPressed() {
        router.onBackPressed()
    }

}
