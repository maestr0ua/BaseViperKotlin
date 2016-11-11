package com.example.akarpenko.basekotlin.base

import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import com.example.akarpenko.basekotlin.R

/**
 * Created by alkurop on 03.05.16.
 */

interface IBaseView {
    fun getString(@StringRes stringRes: Int): String
    fun initUI()

    fun setToolbarTitle(@StringRes textRes: Int)
    fun setToolbarTitle(text: String)

    fun getActivityContext(): AppCompatActivity
    fun showProgress(@StringRes message: Int = R.string.progress_loading)
    fun hideProgress()
    fun showDialog(dialog: DialogFragment)
    fun hideKeyboard()
    fun showSnackBar(message: String?, actionTitle: String? = null, onAction: (() -> Unit)? = null)
    fun showSnackBar(@StringRes message: Int, @StringRes actionTitle: Int = 0, onAction: (() -> Unit)? = null) {
        if (actionTitle == 0)
            showSnackBar(getString(message), null, onAction)
        else
            showSnackBar(getString(message), getString(actionTitle), onAction)
    }

    fun getFragmentContainer(): Int
    fun getLayout(): Int
}