package com.example.akarpenko.basekotlin

import android.os.Bundle
import com.example.akarpenko.basekotlin.base.ActivityView
import com.example.akarpenko.basekotlin.base.BaseActivityPresenter

/**
 * Created by akarpenko on 21.10.16.
 */

class MainPresenter(override val view: MainView): BaseActivityPresenter(view) {

    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)

        router.replaceFragment(TestFragment(), false)
    }

    fun test() {
        view.test()
    }

}

interface MainView: ActivityView {

    fun test()

}
