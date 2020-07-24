package com.zhenpin.luxurystore

import com.zhen.base.net.BaseResult
import com.zhen.mvvm.base.BaseModel
import com.zhenpin.luxurystore.model.GetWelcomeImgResultBean
import com.zhenpin.luxurystore.net.AppNetWork

class AppRepository private constructor(
    private val netWork: AppNetWork
) : BaseModel() {

    suspend fun getHomeImg(resolutionRatio: String): BaseResult<GetWelcomeImgResultBean> {
        return netWork.getHomeImg(resolutionRatio)
    }

    companion object {

        @Volatile
        private var INSTANCE: AppRepository? = null

        fun getInstance(netWork: AppNetWork) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppRepository(netWork).also { INSTANCE = it }
            }
    }
}