package com.zhenpin.luxurystore.net

import com.zhen.base.net.RetrofitClient

class AppNetWork {
    private val homeService by lazy { RetrofitClient.getInstance().create(AppApi::class.java) }

    private var params: MutableMap<String, String> = HashMap()

    suspend fun getHomeImg(resolutionRatio: String) =
        homeService.getHomeImg(getData(resolutionRatio))

    private fun getData(resolutionRatio: String): MutableMap<String, String> {
        params["resolutionRatio"] = resolutionRatio
        params["networkType"] = ""
        return params
    }

    companion object {
        @Volatile
        private var netWork: AppNetWork? = null

        fun getInstance() = netWork
            ?: synchronized(this) {
            netWork
                ?: AppNetWork()
                    .also { netWork = it }
        }
    }

}