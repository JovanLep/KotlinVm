package com.zhenpin.luxurystore.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.zhen.base.bases.BaseBarActivity
import com.zhen.base.mmkv.SpUtils
import com.zhen.base.utils.DimenUtils.dip2px
import com.zhen.base.utils.DimenUtils.getScreenSize
import com.zhenpin.luxurystore.AppViewModel
import com.zhenpin.luxurystore.R
import com.zhenpin.luxurystore.databinding.ActivityWelcomeBinding
import kotlinx.android.synthetic.main.activity_welcome.*
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

        })

    }

    fun startGuide() {
        ARouter.getInstance().build("/home/main").navigation()
    }


    /**
     * 开启动画
     */
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
        val contentMargin: Int = (getScreenSize(this@WelcomeActivity)?.get(0)!! - contentLength) / 2
        // 中间的img_Introduce保持不动
        val introduceLeft = contentMargin + logoWidth + introduceMargin
        img_introduce.x = introduceLeft * 0f
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
                img_shape.post {
                    val count: Int? = SpUtils.decodeInt("count")
                    if (count == 0) {
                        try {
                            Thread.sleep(100)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
//                        postToEvent(EventBean(), GO_TO_GUIDE)
                    } else {
//                        postToEvent(EventBean(), LOAD_WELCOME_DATA)
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }


}