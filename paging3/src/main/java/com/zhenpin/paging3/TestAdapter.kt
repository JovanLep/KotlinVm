package com.zhenpin.paging3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView

class TestAdapter : PagingDataAdapter<TestBean.Data.Datas, TestAdapter.TestHolder>(CompareTest.postComparator) {

    override fun onBindViewHolder(holder: TestHolder, position: Int) {
        holder.showTv.text = getItem(position)?.tip
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {
        return TestHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_text_layout, parent, false)
        )
    }

    class TestHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val showTv: TextView = itemView.findViewById(R.id.tv_item_show)
    }

}