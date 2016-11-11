package com.example.akarpenko.basekotlin

import com.example.akarpenko.basekotlin.base.BaseFragment
import com.example.akarpenko.basekotlin.dagger.component.ViewComponent
import javax.inject.Inject

/**
 * Created by akarpenko on 21.10.16.
 */

class TestFragment : BaseFragment<TestPresenter>(), TestView {

    override fun getTitle(): Int = R.string.test_title

    @Inject
    lateinit var mPresenter: TestPresenter

    override fun inject(component: ViewComponent) {
        component.inject(this)
    }

    override fun getPresenter() = mPresenter

    override fun initUI() {
        getPresenter().init(this)
    }

    override fun getLayout() = R.layout.fragment_layout



}
