package com.zhen.base.utils

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.youth.banner.util.LogUtils

class TimerManager {
    private var mCountTime: Long? = 0L
    private var mInternal: Long? = 0L
    private var mStartTip: String? = ""
    private var mEndTip: String? = ""
    private var mTextView: TextView? = null
    private var timer: Timer? = null
    var mFinishListener: OnFinishListener? = null


    fun addTime(countTime: Long, internal: Long): TimerManager {
        mCountTime = countTime + 100
        mInternal = internal
        return this
    }

    fun addStartTip(startTip: String): TimerManager {
        mStartTip = startTip
        return this
    }

    fun addEndTip(endTip: String): TimerManager {
        mEndTip = endTip
        return this
    }

    fun addTextView(textView: TextView): TimerManager {
        mTextView = textView
        mTextView?.visibility = View.VISIBLE
        return this
    }

    fun build(): TimerManager {
        timer = Timer(mCountTime!!, mInternal!!)
        return this
    }

    fun addOnFinishListener(finishListener: OnFinishListener): TimerManager {
        mFinishListener = finishListener
        return this
    }


    inner class Timer(countTime: Long, internal: Long) : CountDownTimer(countTime, internal) {
        override fun onFinish() {
            mFinishListener?.onFinish()
        }

        @SuppressLint("SetTextI18n")
        override fun onTick(p0: Long) {
            LogUtils.d("$p0----------------------------")
            mTextView?.text = mStartTip + (p0 / 1000) + mEndTip
        }
    }


    fun start(): TimerManager {
        timer?.start()
        return this
    }

    interface OnFinishListener {
        fun onFinish()
    }

}