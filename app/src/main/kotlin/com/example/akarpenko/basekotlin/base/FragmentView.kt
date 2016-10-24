package com.example.akarpenko.basekotlin.base

import android.support.v4.app.Fragment

interface FragmentView : BaseView {

    fun asFragment(): Fragment
}
