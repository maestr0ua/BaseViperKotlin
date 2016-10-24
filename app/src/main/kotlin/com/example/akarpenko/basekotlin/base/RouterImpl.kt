package com.example.akarpenko.basekotlin.base

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import android.webkit.MimeTypeMap
import com.example.akarpenko.basekotlin.App
import com.example.akarpenko.basekotlin.R
import com.example.akarpenko.basekotlin.dialogs.*
import com.example.akarpenko.basekotlin.utils.KeyboardManager
import com.example.akarpenko.basekotlin.utils.Logger
import java.io.File

class RouterImpl : Router {

    private var mActivity: ActivityView? = null
    private var progressDialog: LoadingDialog? = null
    private var mDialog: BaseDialog? = null


    override fun init(activity: ActivityView, activityPresenter: BaseActivityPresenter) {
        mActivity = activity
/*
        mActivity!!.asActivity().supportFragmentManager.addOnBackStackChangedListener {
            val fragment = mActivity!!.asActivity().supportFragmentManager.findFragmentById(mActivity!!.fragmentContainer) as BaseFragment<*>
            mActivity!!.setToolbarTitle(fragment.title)
        }

*/
    }

    override fun clearBackStack() {
        mActivity!!.asActivity().supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onBackPressed() {
        if (mActivity!!.asActivity().supportFragmentManager.backStackEntryCount > 0) {
            mActivity!!.asActivity().supportFragmentManager.popBackStack()
        } else
            mActivity!!.finishActivity()

    }

    override fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        replaceFragment(fragment, addToBackStack, null, "")
    }

    override fun replaceFragment(fragment: Fragment, addToBackStack: Boolean, sharedElement: View?, sharedName: String?) {
        hideKeyboard()
        if (mActivity!!.fragmentContainer == 0) {
            UnsupportedOperationException("There are not container for fragment" + javaClass.name)
        } else {

            val fragmentTransaction = mActivity!!.asActivity().supportFragmentManager.beginTransaction()
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null)
            }

            val currFragment = mActivity!!.asActivity().supportFragmentManager.findFragmentById(mActivity!!.fragmentContainer)
            if (currFragment != null) fragmentTransaction.hide(currFragment)

            if (mActivity!!.asActivity().supportFragmentManager.backStackEntryCount > 0 || addToBackStack) {
                fragmentTransaction.add(mActivity!!.fragmentContainer, fragment, fragment.javaClass.name)
            } else {
                fragmentTransaction.replace(mActivity!!.fragmentContainer, fragment, fragment.javaClass.name)
            }
            fragmentTransaction.show(fragment)

            if (sharedElement != null)
                fragmentTransaction.addSharedElement(sharedElement, sharedName)

            fragmentTransaction.commit()
            mActivity!!.setToolbarTitle((fragment as BaseFragment<*>).title)
        }

    }

    override fun startActivityForResult(activityClass: Class<*>, flags: Int, bundle: Bundle?, requestCode: Int, vararg sharedViews: View) {
        val intent = Intent(mActivity!!.asActivity(), activityClass)
        if (flags != 0) intent.flags = flags
        if (bundle != null) intent.putExtras(bundle)
        mActivity!!.asActivity().startActivityForResult(intent, requestCode)
    }

    override fun startActivityFromFragment(fragment: Fragment, activityClass: Class<*>, flags: Int, bundle: Bundle?, requestCode: Int, vararg sharedViews: View) {
        val intent = Intent(mActivity!!.asActivity(), activityClass)
        if (flags != 0) intent.flags = flags
        if (bundle != null) intent.putExtras(bundle)
        mActivity!!.asActivity().startActivityFromFragment(fragment, intent, requestCode)
    }

    override fun startActivity(activityClass: Class<*>, flags: Int, bundle: Bundle?, vararg sharedViews: View) {
        val intent = Intent(mActivity!!.asActivity(), activityClass)
        if (flags != 0) intent.flags = flags
        if (bundle != null) intent.putExtras(bundle)
        mActivity!!.asActivity().startActivity(intent)
    }


    override fun finishActivity() {
        mActivity!!.finishActivity()
    }

    override fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = mActivity!!.asActivity().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    override fun hideKeyboard() {
        KeyboardManager.hideKeyboard(mActivity!!.asActivity())
    }

/*
    override fun setToolBarTitle(title: String) {
        mActivity!!.setToolbarTitle(title)
    }
*/

    override fun showRetryDialog(@StringRes title: Int, @StringRes message: Int, positiveListener: View.OnClickListener?, negativeListener: View.OnClickListener?) {
        val dialog = RetryDialog()
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setPositiveButtonTitle(R.string.text_btn_retry)
        dialog.setOnPositiveClickListener(positiveListener)
        dialog.setOnNegativeClickListener(negativeListener)
        showDialog(dialog)
    }

    override fun showSuccessDialog(@StringRes message: Int, listener: View.OnClickListener?) {
        showDialog(SuccessDialog(), 0, message, listener, null)
    }

    override fun showInfoDialog(@StringRes title: Int, @StringRes message: Int, listener: View.OnClickListener?) {
        showDialog(InfoDialog(), title, message, listener, null)
    }

    override fun showErrorDialog(@StringRes title: Int, @StringRes message: Int, listener: View.OnClickListener?) {
        showDialog(ErrorDialog(), title, message, listener, null)
    }

    override fun showErrorDialog(@StringRes title: Int, message: String?, listener: View.OnClickListener?) {
        showDialog(ErrorDialog(), title, message, listener, null)
    }

    override fun showWarningDialog(@StringRes title: Int, @StringRes message: Int, positiveListener: View.OnClickListener?, negativeListener: View.OnClickListener?) {
        showDialog(WarningDialog(), title, message, positiveListener, negativeListener)
    }

    override fun showWarningDialog(@StringRes title: Int, @StringRes message: Int, positiveListener: View.OnClickListener?) {
        val dialog = WarningDialog()
        dialog.setNegativeButtonVisible(false)
        showDialog(dialog, title, message, positiveListener, null)
    }

    override fun showConfirmDialog(@StringRes title: Int, @StringRes message: Int, listener: View.OnClickListener?) {
        showDialog(ConfirmDialog(), title, message, listener, null)
    }

    override fun showConfirmDialog(@StringRes title: Int, @StringRes message: Int, positiveListener: View.OnClickListener?, negativeListener: View.OnClickListener?) {
        showDialog(ConfirmDialog(), title, message, positiveListener, negativeListener)
    }


    override fun showDialog(dialog: BaseDialog, @StringRes title: Int, @StringRes message: Int,
                            positiveListener: View.OnClickListener?, negativeListener: View.OnClickListener?) {
        if (progressDialog != null && progressDialog!!.isShowing) progressDialog!!.dismiss()
        if (mDialog != null && mDialog!!.isVisible()) mDialog!!.dismiss()
        mDialog = dialog
        mDialog!!.setTitle(title)
        mDialog!!.setMessage(message)
        mDialog!!.setOnPositiveClickListener(positiveListener)
        mDialog!!.setOnNegativeClickListener(negativeListener)
        mDialog!!.show(mActivity!!.asActivity().supportFragmentManager, "")

    }

    override fun showDialog(dialog: BaseDialog, @StringRes title: Int, message: String?,
                            listener: View.OnClickListener?, negativeListener: View.OnClickListener?) {
        if (progressDialog != null && progressDialog!!.isShowing) progressDialog!!.dismiss()
        if (mDialog != null && mDialog!!.isVisible) mDialog!!.dismiss()
        mDialog = dialog
        mDialog?.setTitle(title)
        mDialog?.setMessage(message)
        mDialog?.setOnPositiveClickListener(listener)
        mDialog?.setOnNegativeClickListener(negativeListener)
        mDialog?.show(mActivity!!.asActivity().supportFragmentManager, "")
    }

    override fun showDialog(dialog: BaseDialog) {
        if (mDialog != null && mDialog!!.isVisible) mDialog!!.dismiss()
        mDialog = dialog
        mDialog!!.show(mActivity!!.asActivity().supportFragmentManager, "")
    }

    override fun showLoadingDialog() {
        if (mActivity!!.isActivityResumed) {
            if (progressDialog == null) {
                progressDialog = LoadingDialog()
            }
            if (!progressDialog!!.isShowing && App.instance.isForeground)
                progressDialog!!.show(mActivity!!.asActivity().supportFragmentManager, "")
        }
    }

    override fun hideLoadingDialog() {

        try {
            if (progressDialog != null && progressDialog!!.isShowing && !progressDialog!!.isDismiss)
                progressDialog!!.dismiss()
        } catch (e: IllegalStateException) {
            Logger.e(e)
        }

    }

}
