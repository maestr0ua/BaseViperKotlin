package com.example.akarpenko.basekotlin.dialogs

import android.view.View
import com.example.akarpenko.basekotlin.R

class WarningDialog : ConfirmDialog() {

    private var isNegativeVisible: Boolean = false

    override fun getLayout() = R.layout.dialog_warning_layout

    override fun initUI() {
        super.initUI()
        btnNegative.visibility = if (isNegativeVisible) View.VISIBLE else View.GONE
    }

    fun setNegativeButtonVisible(isVisible: Boolean) {
        isNegativeVisible = isVisible
    }

}
