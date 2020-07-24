package com.zhen.base.mmkv

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object SpUtils {
    var mmkv: MMKV? = null

    init {
        mmkv = MMKV.defaultMMKV()
    }

    fun encode(key: String, value: Any?) {
        when (value) {
            is String -> mmkv?.encode(key, value)
            is Float -> mmkv?.encode(key, value)
            is Boolean -> mmkv?.encode(key, value)
            is Int -> mmkv?.encode(key, value)
            is Long -> mmkv?.encode(key, value)
            is Double -> mmkv?.encode(key, value)
            is ByteArray -> mmkv?.encode(key, value)
            is Nothing -> return
        }
    }

    fun <T : Parcelable> encode(key: String, t: T?) {
        if(t ==null){
            return
        }
        mmkv?.encode(key, t)
    }

    fun encode(key: String, sets: Set<String>?) {
        if(sets ==null){
            return
        }
        mmkv?.encode(key, sets)
    }

    fun decodeInt(key: String): Int? {
        return mmkv?.decodeInt(key, 0)
    }

    fun decodeDouble(key: String): Double? {
        return mmkv?.decodeDouble(key, 0.00)
    }

    fun decodeLong(key: String): Long? {
        return mmkv?.decodeLong(key, 0L)
    }

    fun decodeBoolean(key: String): Boolean? {
        return mmkv?.decodeBool(key, false)
    }

    fun decodeFloat(key: String): Float? {
        return mmkv?.decodeFloat(key, 0F)
    }

    fun decodeByteArray(key: String): ByteArray? {
        return mmkv?.decodeBytes(key)
    }

    fun decodeString(key: String): String? {
        return mmkv?.decodeString(key, "")
    }

    fun <T : Parcelable> decodeParcelable(key: String, tClass: Class<T>): T? {
        return mmkv?.decodeParcelable(key, tClass)
    }

    fun decodeStringSet(key: String): Set<String>? {
        return mmkv?.decodeStringSet(key, Collections.emptySet())
    }

    fun removeKey(key: String) {
        mmkv?.removeValueForKey(key)
    }

    fun clearAll() {
        mmkv?.clearAll()
    }

}


private inline fun <T> MMKV.delegate(
    key: String? = null,
    defaultValue: T,
    crossinline getter: MMKV.(String, T) -> T,
    crossinline setter: MMKV.(String, T) -> Boolean
): ReadWriteProperty<Any, T> =
    object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T =
            getter(key ?: property.name, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            setter(key ?: property.name, value)
        }
    }

fun MMKV.boolean(
    key: String? = null,
    defaultValue: Boolean = false
): ReadWriteProperty<Any, Boolean> =
    delegate(key, defaultValue, MMKV::decodeBool, MMKV::encode)

fun MMKV.int(key: String? = null, defaultValue: Int = 0): ReadWriteProperty<Any, Int> =
    delegate(key, defaultValue, MMKV::decodeInt, MMKV::encode)

fun MMKV.long(key: String? = null, defaultValue: Long = 0L): ReadWriteProperty<Any, Long> =
    delegate(key, defaultValue, MMKV::decodeLong, MMKV::encode)

fun MMKV.float(key: String? = null, defaultValue: Float = 0.0F): ReadWriteProperty<Any, Float> =
    delegate(key, defaultValue, MMKV::decodeFloat, MMKV::encode)

fun MMKV.double(
    key: String? = null,
    defaultValue: Double = 0.0
): ReadWriteProperty<Any, Double> =
    delegate(key, defaultValue, MMKV::decodeDouble, MMKV::encode)

private inline fun <T> MMKV.nullableDefaultValueDelegate(
    key: String? = null,
    defaultValue: T?,
    crossinline getter: MMKV.(String, T?) -> T,
    crossinline setter: MMKV.(String, T) -> Boolean
): ReadWriteProperty<Any, T> =
    object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T =
            getter(key ?: property.name, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            setter(key ?: property.name, value)
        }
    }

fun MMKV.byteArray(
    key: String? = null,
    defaultValue: ByteArray? = null
): ReadWriteProperty<Any, ByteArray> =
    nullableDefaultValueDelegate(key, defaultValue, MMKV::decodeBytes, MMKV::encode)

fun MMKV.string(
    key: String? = null,
    defaultValue: String? = null
): ReadWriteProperty<Any, String> =
    nullableDefaultValueDelegate(key, defaultValue, MMKV::decodeString, MMKV::encode)

fun MMKV.stringSet(
    key: String? = null,
    defaultValue: Set<String>? = null
): ReadWriteProperty<Any, Set<String>> =
    nullableDefaultValueDelegate(key, defaultValue, MMKV::decodeStringSet, MMKV::encode)

inline fun <reified T : Parcelable> MMKV.parcelable(
    key: String? = null,
    defaultValue: T? = null
): ReadWriteProperty<Any, T> =
    object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T =
            decodeParcelable(key ?: property.name, T::class.java, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            encode(key ?: property.name, value)
        }
    }
