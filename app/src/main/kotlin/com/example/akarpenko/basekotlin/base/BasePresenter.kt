package com.example.akarpenko.basekotlin.base

import android.os.Bundle

interface BasePresenter {

    fun onCreateView(savedInstanceState: Bundle?)

    fun onDestroyView()

    fun onPause()

    fun onResume()

    fun onViewCreated()

    fun onBackPressed()

    fun onSaveInstanceState(outState: Bundle)

    fun onRestoreInstanceState(savedInstanceState: Bundle)

}
