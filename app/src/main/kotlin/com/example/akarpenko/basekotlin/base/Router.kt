package com.example.akarpenko.basekotlin.base


import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.View
import com.example.akarpenko.basekotlin.dialogs.BaseDialog

interface Router {

    fun init(activity: ActivityView, activityPresenter: BaseActivityPresenter)

    fun onBackPressed()

    fun clearBackStack()

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean)

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean, sharedElement: View?, sharedName: String?)

    fun showRetryDialog(@StringRes title: Int, @StringRes message: Int, positiveListener: View.OnClickListener?, negativeListener: View.OnClickListener?)

    fun showSuccessDialog(@StringRes message: Int, listener: View.OnClickListener?)

    fun showInfoDialog(@StringRes title: Int, @StringRes message: Int, listener: View.OnClickListener?)

    fun showErrorDialog(@StringRes title: Int, @StringRes message: Int, listener: View.OnClickListener?)

    fun showErrorDialog(@StringRes title: Int, message: String?, listener: View.OnClickListener?)

    fun showConfirmDialog(@StringRes title: Int, @StringRes message: Int, listener: View.OnClickListener?)

    fun showConfirmDialog(@StringRes title: Int, @StringRes message: Int, positiveListener: View.OnClickListener?, negativeListener: View.OnClickListener?)

    fun showWarningDialog(@StringRes title: Int, @StringRes message: Int, positiveListener: View.OnClickListener?, negativeListener: View.OnClickListener?)

    fun showWarningDialog(@StringRes title: Int, @StringRes message: Int, positiveListener: View.OnClickListener?)

    fun showDialog(dialog: BaseDialog, @StringRes title: Int, @StringRes message: Int, positiveListener: View.OnClickListener?, negativeListener: View.OnClickListener?)

    fun showDialog(dialog: BaseDialog, @StringRes title: Int, message: String?, listener: View.OnClickListener?, negativeListener: View.OnClickListener?)

    fun showDialog(dialog: BaseDialog)

    fun showLoadingDialog()

    fun hideLoadingDialog()

    fun startActivity(activityClass: Class<*>, flags: Int, bundle: Bundle?, vararg sharedViews: View)

    fun startActivityForResult(activityClass: Class<*>, flags: Int, bundle: Bundle?, requestCode: Int, vararg sharedViews: View)

    fun startActivityFromFragment(fragment: Fragment, activityClass: Class<*>, flags: Int, bundle: Bundle?, requestCode: Int, vararg sharedViews: View)

    fun finishActivity()

    fun isServiceRunning(serviceClass: Class<*>): Boolean

    fun hideKeyboard()

//    fun setToolBarTitle(title: String)

}
