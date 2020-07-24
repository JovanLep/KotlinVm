package com.zhenpin.luxurystore.net

import com.zhen.base.net.ApiService
import com.zhen.base.net.BaseResult
import com.zhenpin.luxurystore.model.GetWelcomeImgResultBean
import com.zhenpin.main.HttpContract.API_HOME_URL
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AppApi : ApiService {

    /**
     * 获取欢迎页图片
     *
     * @param map 请求参数集合
     * @return 观察者
     */
    @FormUrlEncoded
    @POST(API_HOME_URL + "newEditionHomePage/getHomeImg.json")
    fun getHomeImg(@FieldMap map: Map<String, String>): BaseResult<GetWelcomeImgResultBean>


}