package com.zhenpin.main.category

import com.zhen.mvvm.base.BaseFragment
import com.zhenpin.main.R
import com.zhenpin.main.databinding.FragmentCateBinding
import com.zhenpin.main.viewmodel.MainViewModel

class CategoryFragment :BaseFragment<MainViewModel,FragmentCateBinding>(){
    override fun layoutId(): Int = R.layout.fragment_cate

}