package com.example.akarpenko.basekotlin

import android.support.v7.widget.Toolbar
import com.example.akarpenko.basekotlin.base.BaseActivity
import com.example.akarpenko.basekotlin.dagger.component.ViewComponent
import javax.inject.Inject

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    @Inject
    lateinit var mPresenter: MainPresenter

    private var mToolbar: Toolbar? = null

    override fun getLayout(): Int = R.layout.activity_main

    override fun getFragmentContainer(): Int = R.id.container

    override fun getPresenter() = mPresenter

    override fun inject(component: ViewComponent) {
        component.inject(this)
    }

    override fun initUI() {
        getPresenter().init(this)

        mToolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(mToolbar)
    }

    override fun test() {

    }


}
