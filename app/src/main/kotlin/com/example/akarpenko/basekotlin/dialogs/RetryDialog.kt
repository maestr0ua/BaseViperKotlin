package com.example.akarpenko.basekotlin.dialogs

import com.example.akarpenko.basekotlin.R

class RetryDialog : ConfirmDialog() {

    protected override val layoutResource: Int
        get() = R.layout.dialog_retry_layout
}
