package com.example.akarpenko.basekotlin.dialogs

import android.view.View
import com.example.akarpenko.basekotlin.R

class ErrorDialog : InfoDialog() {

    protected override val layoutResource: Int
        get() = R.layout.dialog_error_layout

    override fun setupViews(_rootView: View) {
        super.setupViews(_rootView)
        isCancelable = false
        if (tvTitle.text.toString().isEmpty()) {
            tvTitle.setText(R.string.error)
        }
    }
}
