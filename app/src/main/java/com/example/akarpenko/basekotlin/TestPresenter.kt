package com.example.akarpenko.basekotlin

import com.example.akarpenko.basekotlin.base.BasePresenter
import com.example.akarpenko.basekotlin.base.IBaseView
import com.example.akarpenko.basekotlin.dagger.component.PresenterComponent

/**
 * Created by akarpenko on 21.10.16.
 */

interface TestView : IBaseView {

}


class TestPresenter() : BasePresenter<TestView>() {

    override fun inject(component: PresenterComponent) {
        component.inject(this)
    }

}
