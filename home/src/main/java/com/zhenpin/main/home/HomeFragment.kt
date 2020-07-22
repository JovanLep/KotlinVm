package com.zhenpin.main.home

import com.zhen.mvvm.base.BaseFragment
import com.zhenpin.main.R
import com.zhenpin.main.databinding.FragmentHomeBinding
import com.zhenpin.main.viewmodel.MainViewModel

class HomeFragment : BaseFragment<MainViewModel, FragmentHomeBinding>() {
    override fun layoutId(): Int = R.layout.fragment_home
}