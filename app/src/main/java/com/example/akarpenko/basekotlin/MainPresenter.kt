package com.example.akarpenko.basekotlin

import com.example.akarpenko.basekotlin.base.BasePresenter
import com.example.akarpenko.basekotlin.base.IBaseView
import com.example.akarpenko.basekotlin.dagger.component.PresenterComponent

/**
 * Created by akarpenko on 21.10.16.
 */

class MainPresenter() : BasePresenter<MainView>() {

    override fun inject(component: PresenterComponent) {

    }

    override fun onViewCreated() {
        super.onViewCreated()
        replaceFragment(TestFragment(), false)
    }

    fun test() {
        view.test()
    }

}

interface MainView : IBaseView {

    fun test()

}
