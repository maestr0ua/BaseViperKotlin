package com.example.akarpenko.basekotlin.base

import android.os.Bundle
import com.example.akarpenko.basekotlin.utils.Logger

abstract class BaseActivityPresenter(override val view: ActivityView) : BasePresenterImpl(view) {

    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        Logger.logStackTrace()
        router = RouterImpl()
        router.init(view, this)
    }

}
