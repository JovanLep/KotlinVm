package com.zhenpin.luxurystore.model

import com.zhen.base.bean.VideoDetailBean
import java.io.Serializable

class WelcomeImgBean : Serializable {
    var andoridImg: String? = null
    var activeUrl: String? = null
    var theme: String? = null
    var shareUrl: String? = null
    var newVideoDetail: VideoDetailBean? = null

    companion object {
        private const val serialVersionUID = 1L
    }

    override fun toString(): String {
        return "GetWelcomeImgResultBean(andoridImg=$andoridImg, activeUrl=$activeUrl, theme=$theme, shareUrl=$shareUrl, newVideoDetail=$newVideoDetail)"
    }


}