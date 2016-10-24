package com.example.akarpenko.basekotlin

import com.example.akarpenko.basekotlin.base.BaseFragmentPresenter
import com.example.akarpenko.basekotlin.base.FragmentView

/**
 * Created by akarpenko on 21.10.16.
 */

interface TestView : FragmentView {

}


class TestPresenter(override val view: TestView) : BaseFragmentPresenter(view) {

    fun testtest() {
    }

}
