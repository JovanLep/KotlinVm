package com.zhen.base.mmkv

import com.blankj.utilcode.util.Utils
import com.tencent.mmkv.MMKV

class RepositoryManager {



    fun init(){
        MMKV.initialize(Utils.getApp())
    }
}