package com.example.akarpenko.basekotlin.dialogs

import android.support.annotation.StringRes
import android.view.View
import android.widget.TextView
import com.example.akarpenko.basekotlin.R
import com.example.akarpenko.basekotlin.utils.RxUtils
import rx.functions.Action1

open class ConfirmDialog : BaseDialog() {

    protected lateinit var tvTitle: TextView
    protected lateinit var tvMessage: TextView
    protected lateinit var btnNegative: TextView
    protected lateinit var btnPositive: TextView

    private var mMessage: Int = 0
    private var mTitle: Int = 0

    private var mButtonPositiveTitle: Int = 0

    private var mPositiveListener: View.OnClickListener? = null
    private var mNegativeListener: View.OnClickListener? = null


    override val layoutResource: Int
        get() = R.layout.dialog_confirm_layout

    override fun setupViews(_rootView: View) {

        tvTitle = _rootView.findViewById(R.id.tvTitle) as TextView
        tvMessage = _rootView.findViewById(R.id.tvMessage) as TextView
        btnNegative = _rootView.findViewById(R.id.btnNegative) as TextView
        btnPositive = _rootView.findViewById(R.id.btnPositive) as TextView

        isCancelable = false

        RxUtils.click(btnNegative, Action1 { o ->
            if (mNegativeListener != null) mNegativeListener!!.onClick(null)
            dismiss()
        })
        RxUtils.click(btnPositive, Action1 { o ->
            if (mPositiveListener != null) mPositiveListener!!.onClick(null)
            dismiss()
        })

        if (mMessage != 0) tvMessage.text = getString(mMessage)
        if (mTitle != 0) tvTitle.text = getString(mTitle)
        if (mButtonPositiveTitle != 0) btnPositive.text = getString(mButtonPositiveTitle)

    }

    override fun setTitle(@StringRes _title: Int) {
        mTitle = _title
    }

    override fun setMessage(@StringRes _message: Int) {
        mMessage = _message
    }

    fun setPositiveButtonTitle(@StringRes _title: Int) {
        mButtonPositiveTitle = _title
    }

    override fun setOnNegativeClickListener(_listener: View.OnClickListener?) {
        mNegativeListener = _listener
    }

    override fun setOnPositiveClickListener(_listener: View.OnClickListener?) {
        mPositiveListener = _listener
    }
}
