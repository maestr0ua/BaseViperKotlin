package com.example.akarpenko.basekotlin.dialogs

import com.example.akarpenko.basekotlin.R

class ErrorDialog : InfoDialog() {

    override fun getLayout() = R.layout.dialog_error_layout

    override fun initUI() {
        super.initUI()
        isCancelable = false
        if (tvTitle.text.toString().isEmpty()) {
            tvTitle.setText(R.string.error)
        }

    }

}
