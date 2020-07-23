package com.zhen.base.net

import android.os.Build
import com.zhen.base.common.Constant.NET_DEBUG
import com.zhen.base.common.Constant.NET_SAND_BOX
import com.zhen.base.BaseApp
import com.zhen.base.BuildConfig
import com.zhen.base.common.Constant.CHANNEL
import com.zhen.base.common.HttpConstant.API_HTTP_PREFIX
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Header 头
 */
class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val builder: Request.Builder = request.newBuilder()
        addHeader(builder, "Accept-Encoding", "identity") // 放乱码
        addHeader(builder, "v", BuildConfig.VERSION_NAME) // 版本号
        addHeader(builder, "p", CHANNEL) // 安卓渠道(4)
        addHeader(builder, "channel", "Zhenpin_008") // 市场分发渠道名
        addHeader(builder, "deviceId", "9ad95b60-2412-356e-97f1-f44601c8dd2d") // 设备id
        addHeader(
            builder,
            "deviceToken",
            ""
        ) //消息推送生成的用于标识设备的id，长度为44位，不能定制和修改
        addHeader(builder, "model", Build.MODEL) // 机型
        addHeader(builder, "release", Build.VERSION.RELEASE) // 安卓系统版本号
        addHeader(builder, "sdk", java.lang.String.valueOf(Build.VERSION.SDK_INT)) // 安卓API版本号
        if (BuildConfig.DEBUG && BaseApp.NET_ENVIRONMENT_TYPE == NET_DEBUG) {
            //线下测试环境
            var httpUrl: String = request.url().toString()
            val replace = httpUrl.substring(0, httpUrl.indexOf("."))
            httpUrl = httpUrl.replace(replace, API_HTTP_PREFIX + "apitest")
            builder.url(httpUrl)
        } else if (BuildConfig.DEBUG && BaseApp.NET_ENVIRONMENT_TYPE == NET_SAND_BOX) {
            //线上测试环境
            val domains: MutableList<String> = ArrayList()
            domains.add(API_HTTP_PREFIX + "pay")
            domains.add(API_HTTP_PREFIX + "order")
            domains.add(API_HTTP_PREFIX + "cart")
            domains.add(API_HTTP_PREFIX + "uc")
            domains.add(API_HTTP_PREFIX + "product")
            domains.add(API_HTTP_PREFIX + "tls")
            domains.add(API_HTTP_PREFIX + "search")
            var httpUrl: String = request.url().toString()
            val replace = httpUrl.substring(0, httpUrl.indexOf("."))
            if (domains.contains(replace)) {
                httpUrl =
                    httpUrl.replace(replace, API_HTTP_PREFIX + "sandbox")
                builder.url(httpUrl)
            }
        }
        return chain.proceed(builder.build())
    }

    private fun addHeader(
        builder: Request.Builder,
        name: String?,
        value: String?
    ) {
        if (name != null && value != null) {
            builder.addHeader(name, value)
        }
    }
}