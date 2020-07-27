package com.zhen.base.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.TextView
import com.zhen.base.R

class MsgView : TextView {

    private var mContext:Context? = null
    private var gd_background:Int ?=null
    private var backgroundColor: Int? = null
    private var cornerRadius : Int?=null
    private var strokeWidth : Int?=null
    private var strokeColor : Int?=null
    private var isRadiusHalfHeight:Boolean?=false
    private var isWidthHeightEqual:Boolean?=false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        this.mContext=context
        obtainAttributes(context, attributeSet!!)
    }

    private fun obtainAttributes(
        context: Context,
        attrs: AttributeSet
    ) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MsgView)
        backgroundColor =
            ta.getColor(R.styleable.MsgView_mv_backgroundColor, Color.TRANSPARENT)
        cornerRadius = ta.getDimensionPixelSize(R.styleable.MsgView_mv_cornerRadius, 0)
        strokeWidth = ta.getDimensionPixelSize(R.styleable.MsgView_mv_strokeWidth, 0)
        strokeColor =
            ta.getColor(R.styleable.MsgView_mv_strokeColor, Color.TRANSPARENT)
        isRadiusHalfHeight = ta.getBoolean(R.styleable.MsgView_mv_isRadiusHalfHeight, false)
        isWidthHeightEqual = ta.getBoolean(R.styleable.MsgView_mv_isWidthHeightEqual, false)
        ta.recycle()
    }
}