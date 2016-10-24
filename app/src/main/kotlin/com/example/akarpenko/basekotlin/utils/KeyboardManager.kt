package com.example.akarpenko.basekotlin.utils

import android.app.Activity
import android.content.Context
import android.os.ResultReceiver
import android.view.View
import android.view.inputmethod.InputMethodManager

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

object KeyboardManager {

    fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isAcceptingText)
            try {
                imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
            } catch (t: Throwable) {

            }

    }

    fun showKeyboard(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (imm != null) {
            var showSoftInputUnchecked: Method? = null
            try {
                showSoftInputUnchecked = imm.javaClass.getMethod("showSoftInputUnchecked", Integer.TYPE, ResultReceiver::class.java)
            } catch (e: NoSuchMethodException) {
                // Log something
            }

            if (showSoftInputUnchecked != null) {
                try {
                    showSoftInputUnchecked.invoke(imm, 0, null)
                } catch (e: IllegalAccessException) {
                    // Log something
                } catch (e: InvocationTargetException) {
                    // Log something
                }

            }
        }
    }
}
