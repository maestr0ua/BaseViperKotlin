package com.example.akarpenko.basekotlin.dialogs

import android.view.View
import com.example.akarpenko.basekotlin.R

class WarningDialog : ConfirmDialog() {

    private var isNegativeVisible: Boolean = false

    protected override val layoutResource: Int = R.layout.dialog_warning_layout

    override fun setupViews(_rootView: View) {
        super.setupViews(_rootView)
        btnNegative.visibility = if (isNegativeVisible) View.VISIBLE else View.GONE
    }


    fun setNegativeButtonVisible(isVisible: Boolean) {
        isNegativeVisible = isVisible
    }

}
