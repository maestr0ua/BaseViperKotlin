package com.example.akarpenko.basekotlin

import com.example.akarpenko.basekotlin.base.BaseActivity

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override val layoutResource: Int = R.layout.activity_main

    override val fragmentContainer: Int = R.id.container

    override fun initPresenter(): MainPresenter? {
        return MainPresenter(this)
    }

    override fun findUI() {
    }

    override fun setupUI() {

    }

    override fun test() {

    }


}
