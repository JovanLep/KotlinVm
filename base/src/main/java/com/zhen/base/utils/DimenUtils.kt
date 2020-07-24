package com.zhen.base.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

/**
 * @author 尺寸工具类
 */
object DimenUtils {
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    fun dip2px(context: Context, dipValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 获取屏幕尺寸
     *
     * @param context 上下文环境
     * @return int[屏幕宽度，屏幕高度]
     */
    fun getScreenSize(context: Context): IntArray {
        val windowManager = (context as Activity).windowManager
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        val width = outMetrics.widthPixels
        val height = outMetrics.heightPixels
        return intArrayOf(width, height)
    }

}