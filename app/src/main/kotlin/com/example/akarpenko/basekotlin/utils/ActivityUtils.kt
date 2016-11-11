package com.example.akarpenko.basekotlin.utils

import android.content.Context
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.akarpenko.basekotlin.base.BaseFragment

/**
 * Created by alkurop on 05.05.16.
 */

fun AppCompatActivity.findCurrentFragment(containerId: Int): BaseFragment<*>? {
    val fragment = this.supportFragmentManager.findFragmentById(containerId)
    if (fragment != null) return fragment as BaseFragment<*>
    else return null
}

fun AppCompatActivity.hideKeyboard() {
    try {
        (this.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
    } catch (e: Exception) {
    }

}

fun AppCompatActivity.interceptTouch(ev: MotionEvent): Boolean {
    if (ev.action === MotionEvent.ACTION_UP) {
        val view = currentFocus

        if (view != null) {

            val viewTmp = currentFocus
            val viewNew = viewTmp ?: view

            if (viewNew == view) {
                val rect = Rect()
                val coordinates = IntArray(2)

                view.getLocationOnScreen(coordinates)

                rect.set(coordinates[0], coordinates[1], coordinates[0] + view.width, coordinates[1] + view.height)

                val x = ev.x.toInt()
                val y = ev.y.toInt()

                if (rect.contains(x, y)) {
                    return false
                }
            } else if (viewNew is EditText || viewNew is AppCompatAutoCompleteTextView) {
                return false
            }

            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(viewNew.windowToken, 0)

            viewNew.clearFocus()

            return false
        }
    }
    return true
}

