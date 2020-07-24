package com.zhen.base.bean

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * 通用的 视频播放封装类
 */
class VideoDetailBean : Serializable, Parcelable {
    private val modelId: String? = null
    var videoFileSize // 视频文件大小 单位M
            : String? = null
    var videoFileMD5 // 视频文件MD5（仅欢迎页返回此字段）
            : String? = null
    var videoTime // 视频播放时长 单位s
            : String? = null
    var videoSource // 视频播放地址
            : String? = null
    var videoImgSource // 视频封面
            : String? = null
    var title // 视频名称
            : String? = null

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(videoFileSize)
        dest.writeString(videoFileMD5)
        dest.writeString(videoTime)
        dest.writeString(videoSource)
        dest.writeString(videoImgSource)
        dest.writeString(title)
    }

    constructor() {}
    protected constructor(`in`: Parcel) {
        videoFileSize = `in`.readString()
        videoFileMD5 = `in`.readString()
        videoTime = `in`.readString()
        videoSource = `in`.readString()
        videoImgSource = `in`.readString()
        title = `in`.readString()
    }

    companion object {
        val CREATOR: Parcelable.Creator<VideoDetailBean?> =
            object : Parcelable.Creator<VideoDetailBean?> {
                override fun createFromParcel(source: Parcel): VideoDetailBean? {
                    return VideoDetailBean(source)
                }

                override fun newArray(size: Int): Array<VideoDetailBean?> {
                    return arrayOfNulls(size)
                }
            }
    }
}