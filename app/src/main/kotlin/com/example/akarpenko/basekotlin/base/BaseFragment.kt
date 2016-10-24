package com.example.akarpenko.basekotlin.base

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.akarpenko.basekotlin.utils.Logger

abstract class BaseFragment<P: BasePresenterImpl>: Fragment(), FragmentView {

    private var mBaseActivity: BaseActivity<*>? = null

    abstract val title: Int

    protected abstract val layoutResource: Int

    protected abstract fun initPresenter(): P?

    var mPresenter: P? = null

    protected abstract fun findUI(rootView: View)

    protected abstract fun setupUI()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        val rootView = inflater!!.inflate(layoutResource, container, false)
        findUI(rootView)

        mBaseActivity = activity as BaseActivity<*>

        if (mPresenter == null) {
            setupUI()
            mPresenter = initPresenter()
            mPresenter?.router = mBaseActivity?.presenter?.router!!
            if (mPresenter == null)
                ClassCastException("Presenter is not created + " + this.javaClass.name)
        }
        mPresenter?.onCreateView(savedInstanceState)

        return rootView
    }

    override val viewArguments: Bundle
        get() = arguments

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.logStackTrace()
        view!!.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                Logger.logStackTrace()
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                mPresenter?.onViewCreated()
            }
        })

    }

    override fun onResume() {
        super.onResume()
        mPresenter?.onResume()
        mBaseActivity?.setToolbarTitle(title)
    }

    override fun onPause() {
        mPresenter?.onPause()
        super.onPause()
    }

    override fun onDestroyView() {
        mPresenter?.onDestroyView()
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun asFragment(): Fragment {
        return this
    }

    override val displaySize: Point
        get() = mBaseActivity?.displaySize!!

    override val viewContext: Context
        get() = context

}
