package com.zhenpin.main.mine

import com.zhen.mvvm.base.BaseFragment
import com.zhenpin.main.R
import com.zhenpin.main.databinding.FragmentMineBinding
import com.zhenpin.main.viewmodel.MainViewModel

class MineFragment : BaseFragment<MainViewModel, FragmentMineBinding>() {
    override fun layoutId(): Int = R.layout.fragment_mine

}