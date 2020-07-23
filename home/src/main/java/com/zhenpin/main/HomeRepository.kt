package com.zhenpin.main

import androidx.lifecycle.LiveData
import com.zhen.base.net.BaseResult
import com.zhen.mvvm.base.BaseModel
import com.zhenpin.main.model.ConfigBean


/**
 *   @auther : Aleyn
 *   time   : 2019/10/29
 */
class HomeRepository private constructor(
    private val netWork: HomeNetWork
) : BaseModel() {

    suspend fun getServiceConfig(refresh: Boolean = false): BaseResult<ConfigBean> {
        return netWork.getServiceConfig()
    }
    
    companion object {

        @Volatile
        private var INSTANCE: HomeRepository? = null

        fun getInstance(netWork: HomeNetWork) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: HomeRepository(netWork).also { INSTANCE = it }
            }
    }
}