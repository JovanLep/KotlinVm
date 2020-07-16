package com.zhenpin.paging3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class TestViewModel :ViewModel(){
    private val repository:TestRepository by lazy { TestRepository() }
    /**
     * Pager 分页入口 每个PagingData代表一页数据 最后调用asLiveData将结果转化为一个可监听的LiveData
     */
    fun getArticleData() = repository.getList().asLiveData()
}