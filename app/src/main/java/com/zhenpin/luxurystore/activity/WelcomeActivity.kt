package com.zhenpin.luxurystore.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.zhen.base.bases.BaseBarActivity
import com.zhen.base.bean.VideoDetailBean
import com.zhen.base.utils.DateUtil.formatTimeOnlyDate2
import com.zhen.base.utils.DimenUtils.dip2px
import com.zhen.base.utils.DimenUtils.getScreenSize
import com.zhen.base.utils.TimerManager
import com.zhenpin.luxurystore.AppViewModel
import com.zhenpin.luxurystore.R
import com.zhenpin.luxurystore.databinding.ActivityWelcomeBinding
import com.zhenpin.luxurystore.model.WelcomeImgBean
import kotlinx.android.synthetic.main.activity_welcome.*
import java.io.File
import kotlin.math.abs

class WelcomeActivity : BaseBarActivity<AppViewModel, ActivityWelcomeBinding>() {

    override fun layoutId(): Int =
        R.layout.activity_welcome

    override fun initView(savedInstanceState: Bundle?) {
        setBarGone()

    }

    override fun initData() {
        val resolution: IntArray = getScreenSize(this)
        val resolutionRatio = resolution[1].toDouble() / resolution[0].toDouble()
        viewModel.getHomeImg(resolutionRatio.toString()).observe(this, Observer {
            it.let {

            }
            TimerManager()
                .addTime(13000, 1000)
                .addTextView(txt_skip)
                .addStartTip("(")
                .addEndTip(")")
                .addOnFinishListener(object : TimerManager.OnFinishListener {
                    override fun onFinish() {
                        txt_skip.visibility = View.GONE
                        Log.e("333", "我这个tv隐藏了")
                    }
                })
                .build()
                .start()
        })

        TimerManager()
            .addTime(3000, 1000)
            .addOnFinishListener(object : TimerManager.OnFinishListener {
                override fun onFinish() {
                    startAnim()
                }
            })
            .build()
            .start()
    }

    fun startGuide() {
        ARouter.getInstance().build("/home/main").navigation()
    }


    private fun startAnim() {
        val logoWidth: Int = img_logo.width
        val introduceWidth: Int = img_introduce.width
        val shapeWidth: Int = img_shape.width
        if (logoWidth == 0 || introduceWidth == 0 || shapeWidth == 0) {
//            img_shape.post({ postToEvent(EventBean(), LOAD_WELCOME_DATA) })
            return
        }
        val introduceMargin: Int = dip2px(this@WelcomeActivity, 10)
        val shapeMargin: Int = dip2px(this@WelcomeActivity, 3)
        // 总宽
        val contentLength =
            logoWidth + introduceMargin + introduceWidth + shapeMargin + shapeWidth
        // 距离左右
        val contentMargin: Int =
            (getScreenSize(this@WelcomeActivity)[0] - contentLength) / 2
        // 中间的img_Introduce保持不动
        val introduceLeft = contentMargin + logoWidth + introduceMargin
        img_introduce.x = introduceLeft * 1f
        val logoCurTranX: Float = img_logo.translationX
        val logoDexTranX: Float = contentMargin - img_logo.x
        val logoTranAnim =
            ObjectAnimator.ofFloat(img_logo, "translationX", logoCurTranX, logoDexTranX)
        logoTranAnim.interpolator = DecelerateInterpolator()
        logoTranAnim.addUpdateListener { valueAnimator: ValueAnimator ->
            val currentValue = valueAnimator.animatedValue as Float
            if (abs(currentValue / logoDexTranX) > 0.4f) {
                if (img_shape.alpha == 0f) {
                    img_shape.alpha = 1f
                }
            }
        }
        val shapeCurTranX: Float = img_shape.translationX
        val shapeDexTranX: Float =
            img_introduce.x + introduceWidth + shapeMargin - img_shape.x
        val shapeTranAnim = ObjectAnimator.ofFloat(
            img_shape,
            "translationX",
            shapeCurTranX,
            shapeDexTranX
        )
        shapeTranAnim.interpolator = DecelerateInterpolator()
        val animator =
            ObjectAnimator.ofFloat(img_introduce, "alpha", 0f, 1f)
        animator.interpolator = AccelerateInterpolator()
        animator.duration = 10
        animator.startDelay = 50
        val animSet = AnimatorSet()
        animSet.play(logoTranAnim).with(shapeTranAnim).with(animator)
        animSet.duration = 300
        animSet.start()
        animSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
//                img_shape.post({
//                    val count: Int = preferences.getInt("count", 0)
//                    if (count == 0) {
//                        try {
//                            Thread.sleep(100)
//                        } catch (e: InterruptedException) {
//                            e.printStackTrace()
//                        }
//                        postToEvent(EventBean(), GO_TO_GUIDE)
//                    } else {
//                        postToEvent(EventBean(), LOAD_WELCOME_DATA)
//                    }
//                })
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }


//    fun setData(welcomeImgBean: WelcomeImgBean) {
//        var videoUrl: String? = null
//        val newVideoDetail: VideoDetailBean? = welcomeImgBean.newVideoDetail
//        if (newVideoDetail != null) {
//            videoUrl = newVideoDetail.videoSource
//            var today = formatTimeOnlyDate2(System.currentTimeMillis())
//            val timesForDay: String = SharePreUtil.getTimesForDayHavePlayVideo(this)
//            val timesForDays = timesForDay.split("_").toTypedArray()
//            Log.i("xxx", "timesForDays:$timesForDay")
//            var videoMD5 = timesForDays[0]
//            var day = timesForDays[1]
//            var times =
//                if (TextUtils.isEmpty(timesForDays[2])) 0 else timesForDays[2].toInt()
//
//            // 同一个视频每天最多播放两次
//            if (today != day || videoMD5 != newVideoDetail.videoFileMD5) {
//                times = 0
//            }
//        }
//        val andoridImg: String? = welcomeImgBean.andoridImg
//        if (!TextUtils.isEmpty(videoUrl) && !(times >= 2 && videoMD5 != null && videoMD5 == newVideoDetail?.videoFileMD5)) {
//            //视频链接不为空并且同一个视频每天播放次数小于2次，开始执行视频方法
//            val filePath: String = FileUtil.getDiskCacheDir(this, "video")
//            val fileName = videoUrl!!.substring(videoUrl!!.lastIndexOf("/") + 1)
//            this.videoCacheFullPath = filePath + File.separator + fileName
//            Log.i("xxx","CacheFileService.start => videoCacheFullPath:$videoCacheFullPath")
//            CacheFileService.start(
//                this,
//                videoUrl,
//                filePath,
//                fileName,
//                newVideoDetail.videoFileMD5
//            )
//            return
//        }
//        if (!TextUtils.isEmpty(andoridImg)) {
//            ImageLoadUtil.loadImage(
//                WelcomeActivity::class.java.simpleName,
//                this,
//                andoridImg,
//               iv_welcome,
//                RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA),
//                GenericTransitionOptions.with(R.anim.alpha_fade_in),
//                object : ZhenPinLoadDrawableImageListener() {
//                    fun loadDrawable(drawable: Drawable?) {
//                        if (isFinishing) {
//                            return
//                        }
//                        val loadAnimation =
//                            AnimationUtils.loadAnimation(
//                                this@WelcomeActivity,
//                                R.anim.alpha_fade_in
//                            )
//                        txt_skip.visibility = View.VISIBLE
//                        txt_skip.startAnimation(loadAnimation)
//                        // url为空或主题为空该页面不予点击
//                        if (TextUtils.isEmpty(welcomeImgBean.activeUrl) || TextUtils.isEmpty(
//                                welcomeImgBean.theme
//                            )
//                        ) {
//                            iv_welcome.setOnClickListener(null)
//                        } else {
//                            iv_welcome.setOnClickListener(this@WelcomeActivity)
//                        }
//                        //开启广告页倒计时
//                        cancelGetImgTimer2()
//                    }
//
//                    fun loadDrawableFailed(errorDrawable: Drawable?) {
//                        cancelGetImgTimer1()
//                        cancelGetImgTimer2()
//                        postToEvent(EventBean(), GO_TO_MAIN)
//                    }
//                })
//            return
//        }
//    }


}