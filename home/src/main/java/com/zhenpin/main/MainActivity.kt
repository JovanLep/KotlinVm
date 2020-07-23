package com.zhenpin.main

import android.os.Bundle
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhen.mvvm.base.BaseActivity
import com.zhenpin.main.databinding.ActivityMainBinding
import com.zhenpin.main.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = "/home/main")
class MainActivity : BaseActivity<HomeViewModel, ActivityMainBinding>() {

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        viewModel.getServiceConfig().observe(this, Observer {
            tv_home_show.text = it.toString()
        })


    }
}