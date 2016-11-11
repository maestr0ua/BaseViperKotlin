package com.example.akarpenko.basekotlin.base

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import com.example.akarpenko.basekotlin.App
import com.example.akarpenko.basekotlin.R
import com.example.akarpenko.basekotlin.dagger.component.DaggerViewComponent
import com.example.akarpenko.basekotlin.dagger.component.ViewComponent
import com.example.akarpenko.basekotlin.dialogs.LoadingDialog
import com.example.akarpenko.basekotlin.utils.Logger
import com.example.akarpenko.basekotlin.utils.findCurrentFragment
import com.example.akarpenko.basekotlin.utils.hideKeyboard

/**
 * Created by alkurop on 10.05.16.
 */


abstract class BaseActivity<P : BasePresenter<out IBaseView>> : AppCompatActivity(), IBaseView {

    private var snackBar: Snackbar? = null
    private var progressDialog: LoadingDialog? = null
    private var mDialog: BaseDialog<*>? = null
    private var resumed: Boolean = false

    abstract fun getPresenter(): P

    override fun getFragmentContainer(): Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        inject(DaggerViewComponent.builder()
                .build())
        initUI()
        getPresenter().onViewCreated()
    }

    abstract fun inject(component: ViewComponent)

    override fun onResume() {
        super.onResume()
        resumed = true
        getPresenter().onResume()
    }

    override fun onPause() {
        super.onPause()
        resumed = false
        getPresenter().onPause()
    }

    override fun onStart() {
        super.onStart()
        getPresenter().onStart()
    }

    override fun onStop() {
        super.onStop()
        getPresenter().onStop()
    }

    override fun onDestroy() {
        getPresenter().onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?)
            = super.onSaveInstanceState(getPresenter().saveView(outState))

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null)
            getPresenter().restoreView(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getPresenter().onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        getPresenter().onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun setToolbarTitle(textRes: Int) {
        if (supportActionBar != null && textRes != 0) {
            supportActionBar!!.setTitle(textRes)
        }
    }

    override fun setToolbarTitle(text: String) {
        if (supportActionBar != null) {
            supportActionBar!!.title = text
        }
    }

    override fun onBackPressed() {
        val fragment = findCurrentFragment(getFragmentContainer())
        if (!fragment?.onBackPressed()!!) {
            return
        }
        super.onBackPressed()
    }

    override fun hideProgress() {
        try {
            if (progressDialog != null && progressDialog!!.isShowing && !progressDialog!!.isDismiss)
                progressDialog!!.dismiss()
        } catch (e: IllegalStateException) {
            Logger.e(e)
        }
    }

    override fun showProgress(message: Int) {
        hideKeyboard()
        if (resumed) {
            if (progressDialog == null) {
                progressDialog = LoadingDialog()
            }
            if (!progressDialog!!.isShowing && App.instance.isForeground)
                progressDialog!!.show(supportFragmentManager, "")
        }

    }

    override fun showDialog(dialog: DialogFragment) {
        if (dialogResumed()) mDialog?.dismiss()
        dialog.show(supportFragmentManager, null)
    }

    override fun hideKeyboard() = (this as AppCompatActivity).hideKeyboard()

    private fun dialogResumed() = !(mDialog?.isVisible ?: false)

    override fun showSnackBar(message: String?, actionTitle: String?, onAction: (() -> Unit)?) {
        val showWithAction = !TextUtils.isEmpty(actionTitle) && onAction != null
        val isShowing = snackBar?.isShownOrQueued ?: false
        if (!isShowing) {
            snackBar = Snackbar.make(window.decorView.rootView, message ?: "", Snackbar.LENGTH_LONG)
        }
        if (showWithAction) {
            snackBar?.setAction(actionTitle, { onAction?.invoke() })
        }
        if (!isShowing) snackBar?.show()

    }

    override fun getActivityContext() = this
}