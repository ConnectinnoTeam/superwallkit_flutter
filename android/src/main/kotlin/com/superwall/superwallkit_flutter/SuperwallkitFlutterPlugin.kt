package com.superwall.superwallkit_flutter

import android.app.Activity
import android.app.Application
import android.os.Debug
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import java.util.concurrent.atomic.AtomicInteger

class SuperwallkitFlutterPlugin :
    FlutterPlugin,
    ActivityAware {
    var host: SuperwallHost? = null

    companion object {
        val reattachementCount = AtomicInteger(0)
        val lock = Object()
        var currentActivity: Activity? = null
    }

    init {
        if (BuildConfig.DEBUG && BuildConfig.WAIT_FOR_DEBUGGER) {
            Debug.waitForDebugger()
        }
    }

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        synchronized(lock) {
            if (host == null) {
                host =
                    SuperwallHost(
                        binaryMessenger = { flutterPluginBinding.binaryMessenger },
                        context = { flutterPluginBinding?.applicationContext as Application },
                    )
            }
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        host = null
    }

    //region ActivityAware

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        currentActivity = binding.activity
    }

    override fun onDetachedFromActivityForConfigChanges() {
        currentActivity = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        currentActivity = binding.activity
    }

    override fun onDetachedFromActivity() {
        currentActivity = null
    }

    //endregion
}
