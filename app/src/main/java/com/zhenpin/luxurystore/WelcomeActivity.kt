package com.zhenpin.luxurystore

import android.os.Bundle
import com.zhen.base.bases.BaseBarActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.zhen.mvvm.base.NoViewModel
import com.zhenpin.luxurystore.databinding.ActivityWelcomeBinding

class WelcomeActivity : BaseBarActivity<NoViewModel, ActivityWelcomeBinding>() {
    override fun layoutId(): Int = R.layout.activity_welcome

    override fun initView(savedInstanceState: Bundle?) {
        setBarGone()
        mBinding?.tvAdd?.setOnClickListener {
//            startActivity(Intent(this,GuideActivity::class.java))
            startGuide()
        }
    }

    override fun initData() {

    }

    fun startGuide() {
        ARouter.getInstance().build("/home/main").navigation()
    }



}