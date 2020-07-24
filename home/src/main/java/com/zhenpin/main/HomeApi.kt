package com.zhenpin.main

import com.zhen.base.net.ApiService
import com.zhen.base.net.BaseResult
import com.zhenpin.main.HttpContract.API_HOME_URL
import com.zhenpin.main.model.ConfigBean
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface HomeApi : ApiService {

    @FormUrlEncoded
    @POST(API_HOME_URL + "newEditionHomePage/serverConfig.json")
    suspend fun getConfig(@FieldMap formMap: Map<String, String>): BaseResult<ConfigBean>


}