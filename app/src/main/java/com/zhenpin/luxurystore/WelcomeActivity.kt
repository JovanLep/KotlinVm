package com.zhenpin.luxurystore

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.zhen.mvvm.base.BaseActivity
import com.zhen.mvvm.base.NoViewModel
import com.zhenpin.luxurystore.databinding.ActivityWelcomeBinding
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity<NoViewModel, ActivityWelcomeBinding>(), (View) -> Unit {
    override fun layoutId(): Int = R.layout.activity_welcome

    override fun initView(savedInstanceState: Bundle?) {
        tv_add.setOnClickListener(View.OnClickListener(this))
    }

    override fun initData() {

    }

    fun startGuide() {
        ARouter.getInstance().build("/app/guide").navigation()
    }

    override fun invoke(p1: View) {
        startGuide()
    }

}