package com.zhen.base.net

import com.zhen.mvvm.base.IBaseResponse

data class BaseResult<T>(
    val codeMsg: String,
    val code: Int,
    val result: T
) : IBaseResponse<T> {

    override fun code() = code

    override fun codeMsg() = codeMsg

    override fun result() = result

    override fun isSuccess() = (code == 200 && result != null)

}