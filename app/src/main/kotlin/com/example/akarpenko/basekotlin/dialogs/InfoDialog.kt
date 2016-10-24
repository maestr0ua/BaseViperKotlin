package com.example.akarpenko.basekotlin.dialogs

import android.support.annotation.StringRes
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.example.akarpenko.basekotlin.R
import com.example.akarpenko.basekotlin.utils.RxUtils
import rx.functions.Action1

open class InfoDialog : BaseDialog() {

    protected lateinit var tvTitle: TextView
    protected lateinit var tvMessage: TextView
    protected lateinit var btnClose: TextView

    @StringRes
    private var mMessageRes: Int = 0
    @StringRes
    private var mTitleRes: Int = 0
    private var mMessage: String? = null

    private var mListener: View.OnClickListener? = null

    protected override val layoutResource: Int = R.layout.dialog_info_layout

    override fun setupViews(rootView: View) {
        isCancelable = false
        tvTitle = rootView.findViewById(R.id.tvTitle) as TextView
        tvMessage = rootView.findViewById(R.id.tvMessage) as TextView
        btnClose = rootView.findViewById(R.id.btnNegative) as TextView


        RxUtils.click(btnClose, Action1 { o -> onClick() })

        if (mMessageRes != 0)
            tvMessage.text = getString(mMessageRes)
        else if (!TextUtils.isEmpty(mMessage)) tvMessage.text = mMessage

        if (mTitleRes != 0) tvTitle.text = getString(mTitleRes)
    }

    private fun onClick() {
        dismiss()
        if (mListener != null) mListener!!.onClick(null)
    }

    override fun setTitle(@StringRes title: Int) {
        if (title != 0)
            mTitleRes = title
    }

    override fun setMessage(title: String?) {
        mMessage = title
    }

    override fun setMessage(@StringRes text: Int) {
        mMessageRes = text
    }

    override fun setOnPositiveClickListener(listener: View.OnClickListener?) {
        mListener = listener
    }
}
