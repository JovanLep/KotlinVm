package com.zhenpin.main.mine

import com.zhen.mvvm.base.BaseFragment
import com.zhenpin.main.R
import com.zhenpin.main.databinding.FragmentMineBinding
import com.zhenpin.main.viewmodel.HomeViewModel

class MineFragment : BaseFragment<HomeViewModel, FragmentMineBinding>() {
    override fun layoutId(): Int = R.layout.fragment_mine

}