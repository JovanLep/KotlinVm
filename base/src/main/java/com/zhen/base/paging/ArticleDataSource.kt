package com.zhen.base.paging

import android.util.Log
import androidx.paging.PagingSource
import java.util.ArrayList

class ArticleDataSource : PagingSource<Int, TestBean.Data.Datas>() {
    private var count = 0

    /**
     * 实现这个方法来触发异步加载(例如从数据库或网络)。 这是一个suspend挂起函数，可以很方便的使用协程异步加载
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TestBean.Data.Datas> {

        return try {
            val page = params.key ?: 0
            //获取网络数据
            val result = getData()
            LoadResult.Page(
                //加载
                data = result.data?.datas!!,
                prevKey = null,
                nextKey = if (result.data?.curpage == result.data?.pageCount) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    private fun getData(): TestBean {

        Log.e("TAG", "getData: "+count+"ci" )

        val dataList: MutableList<TestBean.Data.Datas> = ArrayList()
        for (i in 0..50) {
            val datas = TestBean.Data.Datas()
            datas.tip = "我是i$i 你是u"
            dataList.add(datas)
        }

        val testBean = TestBean.Data()
        testBean.datas = dataList
        count++
        testBean.curpage = count
        testBean.pageCount = 6

        val test = TestBean()
        test.data = testBean
        return test
    }

}
