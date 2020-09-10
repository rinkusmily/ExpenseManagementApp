package com.shrinkcom.expensemanagementapp.activities

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication



class MyApplication : MultiDexApplication(), LifeCycleDelegate {



    override fun onCreate() {
        super.onCreate()
        val lifeCycleHandler = AppLifecycleHandler(this)
        registerLifecycleHandler(lifeCycleHandler)

    }

    override fun onAppBackgrounded() {
        Log.d("DIKSHA", "App in background")





    }

    override fun onAppForegrounded() {
        Log.d("DIKSHA", "App in foreground")



    }

    private fun registerLifecycleHandler(lifeCycleHandler: AppLifecycleHandler) {
        registerActivityLifecycleCallbacks(lifeCycleHandler)
        registerComponentCallbacks(lifeCycleHandler)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }


}
