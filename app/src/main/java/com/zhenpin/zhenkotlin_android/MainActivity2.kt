package com.zhenpin.zhenkotlin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.aleyn.mvvm.base.BaseActivity
import com.aleyn.mvvm.base.BaseViewModel
import com.test_zhenpin.base.room.Word
import com.zhenpin.zhenkotlin_android.databinding.ActivityMain2Binding
import com.zhenpin.zhenkotlin_android.viewmodels.Main2ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

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