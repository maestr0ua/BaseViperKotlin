package com.example.akarpenko.basekotlin.dialogs

import android.support.annotation.StringRes
import android.text.TextUtils
import android.widget.TextView
import com.example.akarpenko.basekotlin.R
import com.example.akarpenko.basekotlin.base.BaseDialog
import com.example.akarpenko.basekotlin.base.BasePresenter
import com.example.akarpenko.basekotlin.base.IBaseView
import com.example.akarpenko.basekotlin.dagger.component.PresenterComponent
import com.example.akarpenko.basekotlin.dagger.component.ViewComponent
import com.example.akarpenko.basekotlin.utils.RxUtils
import javax.inject.Inject

open class InfoDialog : BaseDialog<InfoPresenter>() {

    @Inject lateinit var mPresenter: InfoPresenter
    protected lateinit var tvTitle: TextView
    protected lateinit var tvMessage: TextView
    protected lateinit var btnClose: TextView

    @StringRes private var mMessageRes: Int = 0
    @StringRes private var mTitleRes: Int = 0

    private var mMessage: String? = null

    private var mListener: (() -> Unit)? = null

    override fun getLayout() = R.layout.dialog_info_layout

    override fun inject(component: ViewComponent) {
        component.inject(this)
    }

    override fun getPresenter() = mPresenter

    override fun initUI() {
        isCancelable = false
        tvTitle = view?.findViewById(R.id.tvTitle) as TextView
        tvMessage = view?.findViewById(R.id.tvMessage) as TextView
        btnClose = view?.findViewById(R.id.btnNegative) as TextView

        RxUtils.click(btnClose, { o -> onClick() })

        if (mMessageRes != 0)
            tvMessage.text = getString(mMessageRes)
        else if (!TextUtils.isEmpty(mMessage)) tvMessage.text = mMessage

        if (mTitleRes != 0) tvTitle.text = getString(mTitleRes)
    }

    private fun onClick() {
        dismiss()
        mListener?.invoke()
    }

    fun setTitle(@StringRes title: Int) {
        if (title != 0)
            mTitleRes = title
    }

    fun setMessage(title: String?) {
        mMessage = title
    }

    fun setMessage(@StringRes text: Int) {
        mMessageRes = text
    }

    fun setOnPositiveClickListener(callBack: (() -> Unit)?) {
        mListener = callBack
    }
}


interface InfoView : IBaseView

class InfoPresenter : BasePresenter<InfoView>() {

    override fun inject(component: PresenterComponent) {
    }

}