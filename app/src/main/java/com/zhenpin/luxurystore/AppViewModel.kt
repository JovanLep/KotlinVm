package com.zhenpin.luxurystore

import androidx.lifecycle.MutableLiveData
import com.zhen.mvvm.base.BaseViewModel
import com.zhenpin.luxurystore.model.WelcomeImgBean
import com.zhenpin.luxurystore.net.AppNetWork

class AppViewModel : BaseViewModel() {

    private val homeRepository: AppRepository by lazy {
        AppRepository.getInstance(AppNetWork.getInstance())
    }

   private val service = MutableLiveData<WelcomeImgBean>()

    fun getHomeImg(resolutionRatio: String): MutableLiveData<WelcomeImgBean> {
        launchOnlyResult({
            homeRepository.getHomeImg(resolutionRatio)
        }, {
            service.value = it
        })
        return service
    }
}