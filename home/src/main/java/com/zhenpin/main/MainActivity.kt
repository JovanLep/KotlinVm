package com.zhenpin.main

import android.os.Bundle
import com.zhen.mvvm.base.BaseActivity
import com.zhenpin.main.databinding.ActivityMainBinding
import com.zhenpin.main.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }
}