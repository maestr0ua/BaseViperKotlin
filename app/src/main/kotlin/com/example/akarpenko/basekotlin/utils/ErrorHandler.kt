package com.example.akarpenko.basekotlin.utils

import android.support.annotation.StringRes
import com.example.akarpenko.basekotlin.R
import com.example.akarpenko.basekotlin.base.Router

object ErrorHandler {

    @StringRes
    private val errorRes = R.string.error


    private fun parseErrorBody(throwable: Throwable): String? = throwable.message

    fun onLoginError(router: Router, _throwable: Throwable) {
        handleError(router, _throwable, true)
    }

    fun onError(router: Router, _throwable: Throwable) {
        handleError(router, _throwable, false)
    }

    private fun handleError(router: Router, throwable: Throwable, isLoginError: Boolean) {
        Logger.e(throwable)
        router.showErrorDialog(errorRes, throwable.message, null)
    }

}
