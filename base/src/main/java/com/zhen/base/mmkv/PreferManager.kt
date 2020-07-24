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
        when (`object`) {
            is String -> mmkv?.encode(key, `object` as String?)
            is Int -> mmkv?.encode(key, (`object` as Int?)!!)
            is Boolean -> mmkv?.encode(key, (`object` as Boolean?)!!)
            is Float -> mmkv?.encode(key, (`object` as Float?)!!)
            is Long -> mmkv?.encode(key, (`object` as Long?)!!)
            else -> mmkv?.encode(key, `object` as String?)
        }
    }

    operator fun get(key: String?, defaultObject: Any?): Any? {
        if (mmkv == null) {
            createFile(key)
        }
        return when (defaultObject) {
            is String -> mmkv?.decodeString(key, defaultObject as String?)
            is Int -> mmkv?.decodeInt(key, (defaultObject as Int?)!!)
            is Boolean -> mmkv?.decodeBool(key, (defaultObject as Boolean?)!!)
            is Float -> mmkv?.decodeFloat(key, (defaultObject as Float?)!!)
            is Long -> mmkv?.decodeLong(key, (defaultObject as Long?)!!)
            else -> mmkv?.decodeString(key, defaultObject as String?)
        }
    }

    fun remove(key: String?) {
        mmkv?.remove(key)
    }
}