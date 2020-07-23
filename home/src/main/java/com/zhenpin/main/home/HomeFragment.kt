package com.zhenpin.main.home

import com.zhen.mvvm.base.BaseFragment
import com.zhenpin.main.R
import com.zhenpin.main.databinding.FragmentHomeBinding
import com.zhenpin.main.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override fun layoutId(): Int = R.layout.fragment_home
}