package com.example.akarpenko.basekotlin

import android.view.View
import com.example.akarpenko.basekotlin.base.*

/**
 * Created by akarpenko on 21.10.16.
 */

class TestFragment : BaseFragment<TestPresenter>(), TestView {

    override val title: Int = 0
    override val layoutResource: Int = R.layout.fragment_layout

    override fun initPresenter(): TestPresenter? {
        return TestPresenter(this)
    }

    override fun findUI(rootView: View) {
    }

    override fun setupUI() {
    }

}
