package com.zhenpin.main

import com.zhen.base.net.RetrofitClient

/**
 *   @auther : Aleyn
 *   time   : 2019/11/01
 */
class HomeNetWork {

    private val homeService by lazy { RetrofitClient.getInstance().create(HomeApi::class.java) }

    suspend fun getServiceConfig() = homeService.getConfig(HashMap())

//    suspend fun getHomeList(page: Int) = homeService.getHomeList(page)
//
//    suspend fun getNaviJson() = homeService.naviJson()
//
//    suspend fun getProjectList(page: Int, cid: Int) = homeService.getProjectList(page, cid)
//
//    suspend fun getPopularWeb() = homeService.getPopularWeb()


    companion object {
        @Volatile
        private var netWork: HomeNetWork? = null

        fun getInstance() = netWork ?: synchronized(this) {
            netWork ?: HomeNetWork().also { netWork = it }
        }
    }


}