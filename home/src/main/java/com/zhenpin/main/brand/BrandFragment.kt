package com.zhenpin.main.brand

import com.zhen.mvvm.base.BaseFragment
import com.zhenpin.main.R
import com.zhenpin.main.databinding.FragmentBrandBinding
import com.zhenpin.main.viewmodel.MainViewModel

class BrandFragment :BaseFragment<MainViewModel,FragmentBrandBinding>(){
    override fun layoutId(): Int = R.layout.fragment_brand
}