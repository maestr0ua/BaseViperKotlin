package com.example.akarpenko.basekotlin.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import com.example.akarpenko.basekotlin.R

class LoadingDialog : DialogFragment() {

    var isShowing = false
        private set
    var isDismiss = true
        private set

    override fun onCreate(_savedInstanceState: Bundle?) {
        super.onCreate(_savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(_inflater: LayoutInflater?, _container: ViewGroup?, _savedInstanceState: Bundle?): View? {
        return _inflater!!.inflate(R.layout.dialog_loading, _container, true)
    }

    override fun show(_manager: FragmentManager, _tag: String) {
        if (isShowing) return

        super.show(_manager, _tag)
        isShowing = true
        isDismiss = false
    }

    override fun onCreateDialog(_savedInstanceState: Bundle?): Dialog {
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        // creating the fullscreen dialog
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.alpha_primary_dark)))
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return dialog
    }

    override fun onDismiss(_dialog: DialogInterface?) {
        isShowing = false
        isDismiss = true
        super.onDismiss(_dialog)
    }

    override fun show(_transaction: FragmentTransaction, _tag: String): Int {
        isShowing = true
        isDismiss = false
        return super.show(_transaction, _tag)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        isShowing = false
        isDismiss = true
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        isDismiss = false
        isShowing = true
    }

    override fun onDetach() {
        super.onDetach()
        isShowing = false
        isDismiss = true
    }
}
