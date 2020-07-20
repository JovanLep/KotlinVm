package com.zhen.base.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig

class TestRepository {
    //    fun getList() = Pager(PagingConfig(pageSize = 6)) { ArticleDataSource() }.flow
    fun getList() =
        Pager(PagingConfig(pageSize = 20),
            pagingSourceFactory = { ArticleDataSource() }).flow
}