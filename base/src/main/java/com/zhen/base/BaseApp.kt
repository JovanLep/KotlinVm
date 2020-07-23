package com.zhen.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.pcl.mvvm.common.Constant.NET_RELEASE

open class BaseApp : Application() {


    companion object{
        var NET_ENVIRONMENT_TYPE = 0 //网络环境类型 0-正式环境 1-测试环境 2-沙箱环境
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this);

        //初始化是否是正式环境
        //初始化是否是正式环境
//        if (BuildConfig.DEBUG) {
////            NET_ENVIRONMENT_TYPE = getNetEnvironmentType(applicationContext)
//        } else {
//        }
        NET_ENVIRONMENT_TYPE = NET_RELEASE
    }
}