package com.example.akarpenko.basekotlin.base

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.ViewTreeObserver
import com.example.akarpenko.basekotlin.R

abstract class BaseActivity<P : BaseActivityPresenter> : AppCompatActivity(), ActivityView {

    var presenter: P? = null

    override var isActivityResumed: Boolean = false

    protected abstract fun initPresenter(): P?

    protected abstract fun findUI()

    protected abstract val layoutResource: Int

    var toolbar: Toolbar? = null

    protected abstract fun setupUI()

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (layoutResource != 0)
            setContentView(layoutResource)

        findUI()

        toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        setupUI()

        if (presenter == null) {
            presenter = initPresenter()
        }
        presenter?.onCreateView(savedInstanceState)

        window.decorView.rootView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                window.decorView.rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                presenter?.onViewCreated()
            }
        })
    }

    override fun onDestroy() {
        presenter?.onDestroyView()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle("activity title")
        isActivityResumed = true
        presenter?.onResume()
    }

    override fun onPause() {
        super.onPause()
        isActivityResumed = false
        presenter?.onPause()
    }

    override fun onBackPressed() {
        presenter?.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun setToolbarTitle(text: String) {
        supportActionBar?.title = text
    }

    override fun setToolbarTitle(@StringRes textRes: Int) {
        supportActionBar?.setTitle(textRes)
    }

    override fun finishActivity() {
        supportFinishAfterTransition()
    }

    override val displaySize: Point
        get() {
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            return size
        }

    override val viewContext: Context
        get() = this

    override val viewArguments: Bundle
        get() = intent.extras

    override fun asActivity(): AppCompatActivity {
        return this
    }

}
