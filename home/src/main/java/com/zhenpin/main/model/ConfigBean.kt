package com.zhenpin.main.model

class ConfigBean {
    var submitDelay = 0
    var android_force_version: String? = null
    var android_now_version: String? = null
    var android_tip: String? = null
    var androidMandatoryType //0：不更新，1强制，2普通
            = 0
    var testurl: String? = null
    var testurltwo: String? = null
    var systemTime //当前服务器时间
            : Long = 0
    var newPepleInfo: String? = null
    var newPepleInfoStrong: String? = null

}