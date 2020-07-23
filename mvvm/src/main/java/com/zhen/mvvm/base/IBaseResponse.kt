package com.zhen.mvvm.base

interface IBaseResponse<T> {
    fun code(): Int
    fun codeMsg(): String
    fun result(): T
    fun isSuccess(): Boolean
}