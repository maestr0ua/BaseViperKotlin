package com.example.akarpenko.basekotlin.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import rx.functions.Action1

/**
 * Created by alkurop on 03.05.16.
 */
interface IBasePresenter<V : IBaseView> {
    fun init(view: V)
    fun saveView(bundle: Bundle?): Bundle
    fun restoreView(saveState: Bundle)
    fun onViewCreated()
    fun onPause()
    fun onResume()
    fun onStart()
    fun onStop()
    fun onDestroy()
    fun <T> connectToBus(eventClass: Class<T>, onPublish: Action1<T>)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    fun getCurrentFragment(): Fragment?
}