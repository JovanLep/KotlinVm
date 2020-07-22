package com.zhen.base.mmkv

import android.content.Context
import com.tencent.mmkv.MMKV

class PreferManager {


    private var mmkv: MMKV? = null

    /**
     * Application里面初始化
     */
    fun initPreferManager(context: Context?) {
        MMKV.initialize(context)
    }

    fun createFile(fileName: String?) {
        if (mmkv == null) {
            mmkv = MMKV.mmkvWithID(fileName, MMKV.SINGLE_PROCESS_MODE)
        }
    }

    fun put(key: String?, `object`: Any?) {
        if (mmkv == null) {
            return
        }
        if (`object` is String) {
            mmkv!!.encode(key, `object` as String?)
        } else if (`object` is Int) {
            mmkv!!.encode(key, (`object` as Int?)!!)
        } else if (`object` is Boolean) {
            mmkv!!.encode(key, (`object` as Boolean?)!!)
        } else if (`object` is Float) {
            mmkv!!.encode(key, (`object` as Float?)!!)
        } else if (`object` is Long) {
            mmkv!!.encode(key, (`object` as Long?)!!)
        } else {
            mmkv!!.encode(key, `object` as String?)
        }
    }

    operator fun get(key: String?, defaultObject: Any?): Any? {
        if (mmkv == null) {
            createFile(key)
        }
        if (defaultObject is String) {
            return mmkv!!.decodeString(key, defaultObject as String?)
        } else if (defaultObject is Int) {
            return mmkv!!.decodeInt(key, (defaultObject as Int?)!!)
        } else if (defaultObject is Boolean) {
            return mmkv!!.decodeBool(key, (defaultObject as Boolean?)!!)
        } else if (defaultObject is Float) {
            return mmkv!!.decodeFloat(key, (defaultObject as Float?)!!)
        } else if (defaultObject is Long) {
            return mmkv!!.decodeLong(key, (defaultObject as Long?)!!)
        }
        return null
    }

    fun remove(key: String?) {
        mmkv!!.remove(key)
    }
}