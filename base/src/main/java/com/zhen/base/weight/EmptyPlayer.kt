package com.zhen.base.weight

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.zhen.base.R

class EmptyPlayer : StandardGSYVideoPlayer {
    private var onVideoPlayerClickListener: OnVideoPlayerClickListener? = null

    interface OnVideoPlayerClickListener {
        fun onClick(v: View?)
    }

    constructor(context: Context?, fullFlag: Boolean?)
            : super(context, fullFlag)

    constructor(context: Context?)
            : super(context)

    constructor(context: Context?, attrs: AttributeSet?)
            : super(context, attrs)

    override fun getLayoutId(): Int {
        return R.layout.layout_empty_video
    }

    fun setOnVideoPlayerClickListener(l: OnVideoPlayerClickListener) {
        onVideoPlayerClickListener = l
    }

    override fun init(context: Context) {
        super.init(context)
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL)
    }

    override fun touchSurfaceMoveFullLogic(
        absDeltaX: Float,
        absDeltaY: Float
    ) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY)
        //不给触摸快进，如果需要，屏蔽下方代码即可
        mChangePosition = false

        //不给触摸音量，如果需要，屏蔽下方代码即可
        mChangeVolume = false

        //不给触摸亮度，如果需要，屏蔽下方代码即可
        mBrightness = false
    }

    override fun touchDoubleUp() {
        //不需要双击暂停
    }

    override fun onClick(v: View) {
        super.onClick(v)
        if (onVideoPlayerClickListener != null) {
            onVideoPlayerClickListener!!.onClick(v)
        }
    }
}