package com.zhen.base.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import java.nio.charset.StandardCharsets
import java.util.*

object Utils {
    /**
     * 获取设备ID
     *
     * @param context 上下文环境
     * @return 设备ID
     */
    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String? {
        var deviceId: String? = null
        val androidId =
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        if ("9774d56d682e549c" != androidId) {
            deviceId =
                UUID.nameUUIDFromBytes(androidId.toByteArray(StandardCharsets.UTF_8))
                    .toString()
        } else {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_PHONE_STATE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val telephonyManager =
                    context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                deviceId = telephonyManager.deviceId
            }
            deviceId =
                if (deviceId != null) UUID.nameUUIDFromBytes(deviceId.toByteArray(StandardCharsets.UTF_8))
                    .toString() else UUID.randomUUID().toString()
        }
        return deviceId
    }

}