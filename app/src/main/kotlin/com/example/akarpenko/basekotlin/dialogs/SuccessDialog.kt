package com.example.akarpenko.basekotlin.dialogs

import com.example.akarpenko.basekotlin.R

class SuccessDialog : InfoDialog() {

    override fun getLayout() = R.layout.dialog_success_layout

    override fun initUI() {
        super.initUI()
        isCancelable = false
        tvTitle.setText(R.string.success)

    }
}
