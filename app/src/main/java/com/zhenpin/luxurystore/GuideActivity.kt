package com.zhenpin.luxurystore

import android.os.Bundle
import com.zhen.base.bases.BaseBarActivity
//import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhen.mvvm.base.NoViewModel
import com.zhenpin.luxurystore.databinding.ActivityGuideBinding

@Route(path = "/app/guide")
class GuideActivity : BaseBarActivity<NoViewModel, ActivityGuideBinding>() {
    override fun layoutId(): Int = R.layout.activity_guide
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }
}