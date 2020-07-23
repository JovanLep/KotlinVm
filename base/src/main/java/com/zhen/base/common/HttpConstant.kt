package com.zhen.base.common

import com.pcl.mvvm.common.Constant.NET_RELEASE
import com.zhen.base.BaseApp

object HttpConstant {
    /**
     * API http前缀协议
     */
    var API_HTTP_PREFIX = "https://"

    /**
     * API 测试地址
     */
    var API_TEST_BASE_URL = API_HTTP_PREFIX + "apitest.zhen.com/"

    /**
     * API host 地址
     */
    var API_BASE_URL = if (BaseApp.NET_ENVIRONMENT_TYPE == NET_RELEASE) API_HTTP_PREFIX + "api.zhen.com/zpapi/" else API_TEST_BASE_URL + "zpapi/"
}