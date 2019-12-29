package com.tohami.egyptinnovate.app

import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class Finit private constructor(var application: Application) {
    private fun warmUpFlutterEngine() {
        val flutterEngine = FlutterEngine(application)
        flutterEngine.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        )
        FlutterEngineCache
                .getInstance()
                .put("my_engine_id", flutterEngine)
    }

    operator fun get(activity: AppCompatActivity ,screen: String): Fragment {
        val cachedFragment: Fragment? = activity.supportFragmentManager.findFragmentByTag(TAG_FLUTTER_FRAGMENT)
        return (cachedFragment
                ?: FlutterFragment.withCachedEngine("my_engine_id")
                        .renderMode(FlutterView.RenderMode.texture)
                        .build<FlutterFragment>()) as Fragment
    }

    operator fun get(fragment: Fragment): Fragment {
        var cachedFragment: Fragment? = null
        cachedFragment = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            fragment.childFragmentManager.findFragmentByTag(TAG_FLUTTER_FRAGMENT)
        } else {
            fragment.fragmentManager!!.findFragmentByTag(TAG_FLUTTER_FRAGMENT)
        }
        return (cachedFragment
                ?: FlutterFragment.withCachedEngine("my_engine_id").build<FlutterFragment>()) as Fragment
    }

    companion object {
        private const val TAG_FLUTTER_FRAGMENT = "dsf"
        private var ourInstance: Finit? = null
        fun init(application: Application): Finit? {
            if (ourInstance == null) {
                ourInstance = Finit(application)
                ourInstance!!.warmUpFlutterEngine()
            }
            return ourInstance
        }
    }

}