package com.zhenpin.paging3

import androidx.recyclerview.widget.DiffUtil

object CompareTest {
    var postComparator = object : DiffUtil.ItemCallback<TestBean.Data.Datas>() {
        override fun areItemsTheSame(
            oldItem: TestBean.Data.Datas,
            newItem: TestBean.Data.Datas
        ): Boolean =
            oldItem == newItem


        override fun areContentsTheSame(
            oldItem: TestBean.Data.Datas,
            newItem: TestBean.Data.Datas
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }
}