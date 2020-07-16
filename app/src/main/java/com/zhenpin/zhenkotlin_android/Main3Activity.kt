package com.zhenpin.zhenkotlin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.zhenpin.paging3.PostsLoadStateAdapter
import com.zhenpin.paging3.TestAdapter
import com.zhenpin.paging3.TestViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

class Main3Activity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(TestViewModel::class.java)
    }

    private val adapter: TestAdapter by lazy {
        TestAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val layoutSmart: SmartRefreshLayout = findViewById(R.id.layout_smart);
        val recList: RecyclerView = findViewById(R.id.rec_list);

        recList.layoutManager = LinearLayoutManager(this)

        recList.adapter = adapter.withLoadStateFooter(PostsLoadStateAdapter(adapter))

        viewModel.getArticleData().observe(this, Observer {
            lifecycleScope.launchWhenCreated {
                adapter.submitData(it)
            }
        })

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapter.loadStateFlow.collectLatest {
                if (it.refresh !is LoadState.Loading) {
                    layoutSmart.finishRefresh()
                }
            }
        }

        layoutSmart.setOnRefreshListener {
            adapter.refresh()
        }
    }
}