package com.example.akarpenko.basekotlin.dialogs

import android.support.annotation.StringRes
import android.widget.TextView
import com.example.akarpenko.basekotlin.R
import com.example.akarpenko.basekotlin.base.BaseDialog
import com.example.akarpenko.basekotlin.base.BasePresenter
import com.example.akarpenko.basekotlin.base.IBaseView
import com.example.akarpenko.basekotlin.dagger.component.PresenterComponent
import com.example.akarpenko.basekotlin.dagger.component.ViewComponent
import com.example.akarpenko.basekotlin.utils.RxUtils
import javax.inject.Inject

open class ConfirmDialog : BaseDialog<ConfirmPresenter>() {

    @Inject lateinit var mPresenter: ConfirmPresenter

    private var mMessage: Int = 0
    private var mTitle: Int = 0

    private var mButtonPositiveTitle: Int = 0

    private var mPositiveListener: (() -> Unit)? = null
    private var mNegativeListener: (() -> Unit)? = null

    protected lateinit var tvTitle: TextView
    protected lateinit var tvMessage: TextView
    protected lateinit var btnNegative: TextView
    protected lateinit var btnPositive: TextView

    override fun getLayout() = R.layout.dialog_confirm_layout

    override fun inject(component: ViewComponent) {
        component.inject(this)
    }

    override fun getPresenter() = mPresenter

    override fun initUI() {
        tvTitle = view?.findViewById(R.id.tvTitle) as TextView
        tvMessage = view?.findViewById(R.id.tvMessage) as TextView
        btnNegative = view?.findViewById(R.id.btnNegative) as TextView
        btnPositive = view?.findViewById(R.id.btnPositive) as TextView

        isCancelable = false

        RxUtils.click(btnNegative, { o ->
            mNegativeListener?.invoke()
            dismiss()
        })
        RxUtils.click(btnPositive, { o ->
            mPositiveListener?.invoke()
            dismiss()
        })

        if (mMessage != 0) tvMessage.text = getString(mMessage)
        if (mTitle != 0) tvTitle.text = getString(mTitle)
        if (mButtonPositiveTitle != 0) btnPositive.text = getString(mButtonPositiveTitle)
    }


    fun setTitle(@StringRes _title: Int) {
        mTitle = _title
    }

    fun setMessage(@StringRes _message: Int) {
        mMessage = _message
    }

    fun setPositiveButtonTitle(@StringRes _title: Int) {
        mButtonPositiveTitle = _title
    }

    fun setOnNegativeClickListener(callBack: (() -> Unit)?) {
        mNegativeListener = callBack
    }

    fun setOnPositiveClickListener(callBack: (() -> Unit)?) {
        mPositiveListener = callBack
    }

}

interface ConfirmView : IBaseView

class ConfirmPresenter() : BasePresenter<ConfirmView>() {

    override fun inject(component: PresenterComponent) {
    }

}
