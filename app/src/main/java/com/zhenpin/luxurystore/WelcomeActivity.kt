package com.zhenpin.luxurystore

import android.os.Bundle
//import com.alibaba.android.arouter.launcher.ARouter
import com.zhen.mvvm.base.BaseActivity
import com.zhen.mvvm.base.NoViewModel
import com.zhenpin.luxurystore.databinding.ActivityWelcomeBinding

class WelcomeActivity : BaseActivity<NoViewModel, ActivityWelcomeBinding>() {
    override fun layoutId(): Int = R.layout.activity_welcome

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }

    fun startGuide() {
//        ARouter.getInstance().build("/app/guide").navigation()
    }

}