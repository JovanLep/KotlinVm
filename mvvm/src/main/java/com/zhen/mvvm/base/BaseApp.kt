package com.zhen.mvvm.base

import android.app.Application
import android.content.Context
import com.zhen.mvvm.app.GlobalConfig
import com.zhen.mvvm.app.MvvmLin

open class BaseApp : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MvvmLin.install(GlobalConfig().apply {
            viewModelFactory = ViewModelFactory()
        })
    }
}