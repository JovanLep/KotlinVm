package com.zhen.base.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.zhen.base.R
import com.zhen.base.databinding.ActivityBaseBarBinding
import com.zhen.mvvm.base.BaseActivity
import com.zhen.mvvm.base.BaseViewModel

abstract class BaseBarActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseActivity<VM,DB>(),
    View.OnClickListener {
    private var hasToolBar = true
    private var toolbarBind: ActivityBaseBarBinding? = null
    private var mSavedInstanceState: Bundle? = null

    protected open fun createBinding(savedInstanceState: Bundle?): DB? {
        mSavedInstanceState = savedInstanceState
        if (hasToolBar) {
            toolbarBind = DataBindingUtil.setContentView(
                this,
                R.layout.activity_base_bar
            ) //我们需要定义一个属于toolbar的bind来控制toolbar的样式
        } else {
            mBinding = DataBindingUtil.setContentView(this, layoutId())
        }
        toolbarBind?.ivGobackTopbar?.setOnClickListener(this)
        toolbarBind?.tvBtTopbar?.setOnClickListener(this)
        setInit()
        return mBinding
    }

    override fun setContentView(layoutResID: Int) {
        if (hasToolBar) { //如果是引用toolbar布局的话我们根布局重写一下，需要引入base_activity作为根布局文件，然后把各ui页面的getLayoutId()定义的布局资源添加到根布局文件中去
            super.setContentView(R.layout.activity_base_bar)
            val container: FrameLayout = findViewById(R.id.framelayout_topbar)
            mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this),
                layoutId(),
                container,
                true
            )
        } else { //如果不需要toolbar的话，我们直接就以getLayoutId()的布局资源id作为根布局
            super.setContentView(layoutResID)
        }
    }

    //初始化隐藏两个按钮
    protected open fun setInit() {
        toolbarBind?.tvBtTopbar?.setVisibility(View.GONE)
    }

    //隐藏返回按钮
    protected open fun setBackGone() {
        toolbarBind?.ivGobackTopbar?.setVisibility(View.GONE)
    }

    //隐藏标题栏
    protected open fun setBarGone() {
        hasToolBar = false
        toolbarBind?.barLayout?.setVisibility(View.GONE)
    }

    //设置标题
    protected open fun setTitle(title: String?) {
        toolbarBind?.tvTitleTopbar?.setText(title)
    }

    //设置文字展示内容（同时隐藏图片按钮）
    protected open fun setTvBt(tv: String?) {
        toolbarBind?.tvBtTopbar?.setText(tv)
        toolbarBind?.tvBtTopbar?.setVisibility(View.VISIBLE)
    }

    //文字点击事件
    protected open fun setTvClick() {}

    //点击事件
    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_goback_topbar -> Toast.makeText(
                this,
                this.javaClass.simpleName,
                Toast.LENGTH_SHORT
            ).show()
            R.id.tv_bt_topbar -> setTvClick()
            else -> {
            }
        }
    }

}