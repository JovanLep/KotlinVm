package com.zhenpin.luxurystore

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.zhen.mvvm.base.BaseActivity
import com.zhenpin.luxurystore.databinding.ActivityMain2Binding
import com.zhenpin.luxurystore.viewmodels.Main2ViewModel

class MainActivity2 : BaseActivity<Main2ViewModel, ActivityMain2Binding>() {

    override fun layoutId(): Int = R.layout.activity_main2

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {

        viewModel.allWords.observe(this, Observer {
            for (word in it) {
                Log.e("3333333", "onCreate: " + word.toString())
            }
        })

    }
}