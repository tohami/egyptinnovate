package com.tohami.egyptinnovate.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.facebook.react.ReactApplication
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactRootView


abstract class BaseReactFragment : Fragment() {
    private var mReactRootView: ReactRootView? = null
    private var mReactInstanceManager: ReactInstanceManager? = null

    // This method returns the name of our top-level component to show
    abstract fun getMainComponentName(): String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mReactRootView = ReactRootView(context)
        mReactInstanceManager = (activity!!.application as ReactApplication).reactNativeHost.reactInstanceManager

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return mReactRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mReactRootView!!.startReactApplication(
                mReactInstanceManager,
                getMainComponentName(),
                null
        )
    }

    override fun onPause() {
        super.onPause()
        mReactInstanceManager?.onHostPause(activity)
    }

    override fun onResume() {
        super.onResume()
        mReactInstanceManager?.onHostResume(activity, null)
    }

    override fun onDestroy() {
        super.onDestroy()

        mReactInstanceManager?.onHostDestroy(activity)
//        mReactRootView.unmountReactApplication()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        mReactInstanceManager?.onActivityResult(activity, requestCode, resultCode, data)
    }

}
