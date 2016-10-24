package com.example.akarpenko.basekotlin.base


import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity

interface ActivityView : BaseView {

    var isActivityResumed: Boolean

    val fragmentContainer: Int

    fun setToolbarTitle(@StringRes textRes: Int)

    fun setToolbarTitle(text: String)

    fun finishActivity()

    fun asActivity(): AppCompatActivity

}
