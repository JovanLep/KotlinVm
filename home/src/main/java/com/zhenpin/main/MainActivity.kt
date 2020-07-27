package com.zhenpin.main

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.zhen.base.bases.BaseBarActivity
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhenpin.main.databinding.ActivityMainBinding
import com.zhenpin.main.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = "/home/main")
class MainActivity : BaseBarActivity<HomeViewModel, ActivityMainBinding>() {

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        mBinding?.bnv?.itemIconTintList = null
        mBinding?.imgHomeTabActive?.let {
            it.visibility = GONE
            it.setOnClickListener { mBinding?.bnv?.selectedItemId = R.id.item_bottom_3 }
        }


        mBinding?.bnv?.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_bottom_1 -> {
                    Toast.makeText(this, "No.1", Toast.LENGTH_SHORT).show()
                }
                R.id.item_bottom_2 -> {
                    Toast.makeText(this, "No.2", Toast.LENGTH_SHORT).show()
                }
                R.id.item_bottom_3 -> {
                    if (mBinding?.imgHomeTabActive?.visibility == View.VISIBLE)
                        Toast.makeText(this, "WebView", Toast.LENGTH_SHORT).show()
                    else {
                        mBinding?.imgHomeTabActive?.visibility= VISIBLE
                        Toast.makeText(this, "No.3", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.item_bottom_4 -> {
                    Toast.makeText(this, "No.4", Toast.LENGTH_SHORT).show()
                }
                R.id.item_bottom_5 -> {
                    Toast.makeText(this, "No.5", Toast.LENGTH_SHORT).show()
                }
            }

            return@setOnNavigationItemSelectedListener true
        }


    }

    override fun initData() {
//        viewModel.getServiceConfig().observe(this, Observer {
//            tv_home_show.text = it.toString()
//        })


    }
}