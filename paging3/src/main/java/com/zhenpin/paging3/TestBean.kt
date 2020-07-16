package com.zhenpin.paging3

class TestBean {
    var data: Data? = null

    class Data {
        var datas: List<Datas>? = null
        var curpage = 0
        var pageCount = 0

        class Datas {
            var id: Int = 0
            var tip: String? = null
        }
    }
}