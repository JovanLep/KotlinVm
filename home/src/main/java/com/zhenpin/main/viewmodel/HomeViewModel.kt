package com.zhenpin.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zhen.mvvm.base.BaseViewModel
import com.zhenpin.main.HomeNetWork
import com.zhenpin.main.HomeRepository
import com.zhenpin.main.model.ConfigBean

class HomeViewModel : BaseViewModel() {
    private val homeRepository: HomeRepository by lazy {
        HomeRepository.getInstance(HomeNetWork.getInstance())
    }

    public val service = MutableLiveData<ConfigBean>()

    fun getServiceConfig(): MutableLiveData<ConfigBean> {
        launchOnlyResult({
            homeRepository.getServiceConfig()
        }, {
            service.value = it
        })
        return service
    }


}