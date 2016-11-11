package com.example.akarpenko.basekotlin.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.akarpenko.basekotlin.dagger.component.DaggerViewComponent
import com.example.akarpenko.basekotlin.dagger.component.ViewComponent
import com.example.akarpenko.basekotlin.utils.hideKeyboard

abstract class BaseFragment<out P : BasePresenter<out IBaseView>> : Fragment(), IBaseView {

    override fun getFragmentContainer(): Int = (activity as IBaseView).getFragmentContainer()

    protected abstract fun getTitle(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(DaggerViewComponent.builder()
                .build())
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        if (savedInstanceState != null)
            getPresenter().restoreView(savedInstanceState)
        getPresenter().onViewCreated()
    }

    abstract fun inject(component: ViewComponent)

    abstract fun getPresenter(): P

    override fun onResume() {
        super.onResume()
        getPresenter().onResume()
        (activity as IBaseView).setToolbarTitle(getTitle())
    }

    override fun onPause() {
        getPresenter().onPause()
        super.onPause()
    }

    override fun onStart() {
        super.onStart()
        getPresenter().onStart()
    }

    override fun onStop() {
        super.onStop()
        getPresenter().onStop()
    }

    override fun onDestroyView() {
        getPresenter().onDestroy()
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getPresenter().onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onSaveInstanceState(outState: Bundle?)
            = super.onSaveInstanceState(getPresenter().saveView(outState))

    override fun setToolbarTitle(textRes: Int) {
        if (isAdded) {
            (activity as IBaseView).setToolbarTitle(textRes)
        }
    }

    override fun setToolbarTitle(text: String) {
        if (isAdded) {
            (activity as IBaseView).setToolbarTitle(text)
        }
    }

    override fun hideProgress() {
        if (isAdded) {
            (activity as IBaseView).hideProgress()
        }
    }

    override fun showProgress(message: Int) {
        if (isAdded) {
            (activity as IBaseView).showProgress(message)
        }
    }

    override fun hideKeyboard() {
        if (isAdded) {
            (activity as AppCompatActivity).hideKeyboard()
        }
    }

    override fun showDialog(dialog: DialogFragment) {
        if (isAdded) {
            (activity as IBaseView).showDialog(dialog)
        }
    }

    override fun showSnackBar(message: String?, actionTitle: String?, onAction: (() -> Unit)?) {
        if (isAdded) {
            (activity as IBaseView).showSnackBar(message, actionTitle, onAction)
        }
    }

    open fun onBackPressed(): Boolean = true

    override fun getActivityContext() = activity!! as AppCompatActivity

}