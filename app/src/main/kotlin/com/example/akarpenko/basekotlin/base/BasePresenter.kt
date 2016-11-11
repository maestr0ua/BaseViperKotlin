package com.example.akarpenko.basekotlin.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.akarpenko.basekotlin.dagger.component.DaggerPresenterComponent
import com.example.akarpenko.basekotlin.dagger.component.PresenterComponent
import com.example.akarpenko.basekotlin.utils.*
import com.example.akarpenko.basekotlin.utils.RxUtils.getNewCompositeSubIfUnsubscribed
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

abstract class BasePresenter<V : IBaseView>() : IBasePresenter<V> {

    init {
        inject(DaggerPresenterComponent.builder()
                .build())
    }

    lateinit protected var view: V
    var subscriptions: CompositeSubscription? = null

    abstract fun inject(component: PresenterComponent)

    override fun onViewCreated() {
    }

    override fun init(view: V) {
        this.view = view
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun saveView(bundle: Bundle?) = bundle ?: Bundle()

    override fun restoreView(saveState: Bundle) {
    }

    override fun onDestroy() {
        RxUtils.unsubscribeIfNotNull(subscriptions)
        subscriptions = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    }

    private fun addSubscription(sub: Subscription) {
        if (subscriptions == null) {
            subscriptions = getNewCompositeSubIfUnsubscribed(subscriptions)
        }
        subscriptions?.remove(sub)
        subscriptions?.add(sub)
    }

    override fun <T> connectToBus(eventClass: Class<T>, onPublish: Action1<T>) {
        addSubscription(RxBus.instance
                .connect(eventClass)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onPublish))
    }

    protected fun <T> executeWithoutProgress(request: Observable<T>,
                                             onResult: ((T?) -> Unit)? = null,
                                             onError: ((Throwable) -> Unit)? = null)
            : Subscription = execute(request, onResult, onError, {})


    protected fun <T> execute(request: Observable<T>,
                              onResult: ((T?) -> Unit)? = null,
                              onError: ((Throwable) -> Unit)? = null,
                              onComplete: (() -> Unit)? = null): Subscription {

        if (onComplete == null)
            view.showProgress()

        val subscription = request.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { t -> onExecuteSuccess(t, onResult) },
                        { throwable -> onExecutingError(throwable, onError) },
                        { onExecutingComplete(onComplete) })

        addSubscription(subscription)
        return subscription
    }

    private fun <T> onExecuteSuccess(t: T, onResult: ((T?) -> Unit)?) {
        onResult?.invoke(t)
    }

    protected fun onExecutingError(throwable: Throwable, onError: ((Throwable) -> Unit)?) {
        if (onError != null) {
            Logger.e(throwable)
            onError.invoke(throwable)
        } else {
            ErrorHandler.handleError(throwable, view)
        }
    }

    private fun onExecutingComplete(action: (() -> Unit)?) {
        if (action == null) {
            view.hideProgress()
        } else {
            action.invoke()
        }
    }


    private fun checkPermissions() {
/*
        if (!SharedPrefManager.getInstance().retrievePermissionInited()) {
            executeWithoutProgress(RxPermissions.getInstance(getView().getViewContext())
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.CALL_PHONE),
                    { granted -> SharedPrefManager.getInstance().storePermissionInited(granted) },
                    ???({ Logger.e() }))
        }
*/
    }


    fun replaceFragment(fragment: Fragment, saveInBackStack: Boolean) {
        replaceFragment(fragment, saveInBackStack, 0, 0)
    }

    fun replaceFragment(fragment: Fragment, saveInBackStack: Boolean, inAnim: Int, outAnim: Int) {
        val fragmentTag = (fragment as Any).javaClass.name
        val ft = view.getActivityContext().supportFragmentManager.beginTransaction()

        if (inAnim != 0 && outAnim != 0)
            ft.setCustomAnimations(inAnim, outAnim)

        ft.replace(view.getFragmentContainer(), fragment, fragmentTag)

        if (saveInBackStack) {
            ft.addToBackStack(fragmentTag)
        }
        ft.commit()
    }

    override fun getCurrentFragment(): Fragment? {
        return view.getActivityContext().findCurrentFragment(view.getFragmentContainer())
    }
}