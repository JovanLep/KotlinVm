package com.zhen.base

import android.app.Application
//import com.alibaba.android.arouter.launcher.ARouter

open class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DEBUG) {
//            ARouter.openLog()
//            ARouter.openDebug()
//        }else{
//        }
//        ARouter.init(this);
    }
}