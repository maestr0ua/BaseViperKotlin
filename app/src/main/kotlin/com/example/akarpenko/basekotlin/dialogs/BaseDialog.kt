package com.example.akarpenko.basekotlin.dialogs

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.akarpenko.basekotlin.R
import com.example.akarpenko.basekotlin.utils.RxUtils
import rx.Subscription
import rx.subscriptions.CompositeSubscription

abstract class BaseDialog : DialogFragment() {

    private val mContentResource = layoutResource

    private var mSubscriptions: CompositeSubscription? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        mSubscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(mSubscriptions)

        val rootView = inflater!!.inflate(R.layout.dialog_base_layout, container, false)
        if (mContentResource != 0) {
            val childContainer: ViewGroup = rootView.findViewById(R.id.content_container_BDL) as ViewGroup
            childContainer.removeAllViews()
            val view = inflater.inflate(mContentResource, childContainer, false)
            childContainer.addView(view)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        RxUtils.unsubscribeIfNotNull(mSubscriptions)
    }

    protected fun addSubscription(subscription: Subscription) {
        mSubscriptions!!.remove(subscription)
        mSubscriptions!!.add(subscription)
    }

    protected abstract val layoutResource: Int

    protected abstract fun setupViews(rootView: View)

    open fun setTitle(@StringRes title: Int) {
        UnsupportedOperationException()
    }

    open fun setMessage(title: String?) {
        UnsupportedOperationException()
    }

    open fun setMessage(@StringRes message: Int) {
        UnsupportedOperationException()
    }

    open fun setOnPositiveClickListener(listener: View.OnClickListener?) {
        UnsupportedOperationException()
    }

    open fun setOnNegativeClickListener(listener: View.OnClickListener?) {
        UnsupportedOperationException()
    }


}
