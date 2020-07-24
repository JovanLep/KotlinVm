package com.zhenpin.luxurystore

import androidx.lifecycle.MutableLiveData
import com.zhen.mvvm.base.BaseViewModel
import com.zhenpin.luxurystore.model.GetWelcomeImgResultBean
import com.zhenpin.luxurystore.net.AppNetWork

class AppViewModel : BaseViewModel() {
    private val homeRepository: AppRepository by lazy {
        AppRepository.getInstance(AppNetWork.getInstance())
    }

    val service = MutableLiveData<GetWelcomeImgResultBean>()

    fun getHomeImg(resolutionRatio: String): MutableLiveData<GetWelcomeImgResultBean> {
        launchOnlyResult({
            homeRepository.getHomeImg(resolutionRatio)
        }, {
            service.value = it
        })
        return service
    }
}