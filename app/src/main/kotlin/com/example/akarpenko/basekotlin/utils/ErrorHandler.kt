package com.example.akarpenko.basekotlin.utils

import com.example.akarpenko.basekotlin.base.IBaseView
import com.example.akarpenko.basekotlin.R
import com.example.akarpenko.basekotlin.domain.repositories.network.RxErrorAdapterFactory

object ErrorHandler {

    fun handleError(throwable: Throwable, view: IBaseView) {
        Logger.e(throwable)
        if (throwable is RxErrorAdapterFactory.RetrofitException) {
            when (throwable.kind) {
                RxErrorAdapterFactory.Kind.NETWORK -> {
                    view.showSnackBar(R.string.no_internet_connection)
                    return
                }
                RxErrorAdapterFactory.Kind.HTTP -> {
                    view.showSnackBar(throwable.message)
                    return
                }
                RxErrorAdapterFactory.Kind.UNEXPECTED -> {
                    view.showSnackBar(throwable.message)
                    return
                }
            }
        } else {
            view.showSnackBar(throwable.message)
            throwable.printStackTrace()
        }

    }


}
