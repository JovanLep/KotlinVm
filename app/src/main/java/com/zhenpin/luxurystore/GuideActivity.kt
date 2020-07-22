package com.zhenpin.luxurystore

import android.os.Bundle
//import com.alibaba.android.arouter.facade.annotation.Route
import com.zhen.mvvm.base.BaseActivity
import com.zhen.mvvm.base.NoViewModel
import com.zhenpin.luxurystore.databinding.ActivityGuideBinding

//@Route(path = "/app/guide")
class GuideActivity : BaseActivity<NoViewModel, ActivityGuideBinding>() {
    override fun layoutId(): Int = R.layout.activity_guide
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }
}