package com.example.akarpenko.basekotlin.dialogs

import android.view.View
import com.example.akarpenko.basekotlin.R

class SuccessDialog : InfoDialog() {

    protected override val layoutResource: Int = R.layout.dialog_success_layout

    override fun setupViews(_rootView: View) {
        super.setupViews(_rootView)
        isCancelable = false
        tvTitle.setText(R.string.success)
    }
}
