package com.zhen.base.mmkv

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.text.TextUtils
import com.blankj.utilcode.util.Utils
import com.zhen.base.mmkv.UserInfo.Companion.data
import com.zhen.base.utils.Utils.getDeviceId
import java.util.*

/**
 * mmkv 存储用户信息
 *
 * @author lierpeng
 */
@SuppressLint("StaticFieldLeak")
class UserManager private constructor() {
    private val context: Context = Utils.getApp().applicationContext
    var channel: String? = "channel"
        get() {
            if (TextUtils.isEmpty(field)) {
                field = currentChannel
            }
            return field
        }

    fun quitLogin() {
        PreferManager().remove(KEY_TOKEN)
        PreferManager().remove(KEY_BIRTHDAY)
        PreferManager().remove(KEY_GENDER)
        PreferManager().remove(KEY_HAS_INSTALL_PWD)
        PreferManager().remove(KEY_HAS_LOGIN_PWD)
        PreferManager().remove(KEY_MEMBER_ID)
        PreferManager().remove(KEY_USER_NAME)
        PreferManager().remove(KEY_LOGIN_STATE)
    }

    fun saveLogin(userBean: UserInfo?) {
        if (userBean == null) {
            PreferManager().remove(KEY_TOKEN)
            PreferManager().remove(KEY_BIRTHDAY)
            PreferManager().remove(KEY_GENDER)
            PreferManager().remove(KEY_HAS_INSTALL_PWD)
            PreferManager().remove(KEY_HAS_LOGIN_PWD)
            PreferManager().remove(KEY_MEMBER_ID)
            PreferManager().remove(KEY_USER_NAME)
            PreferManager().remove(KEY_MOBILE)
            PreferManager().remove(KEY_LOGIN_STATE)
            return
        }
        if (!TextUtils.isEmpty(data!!.accessToken)) {
            accessToken = data?.accessToken
        }
        if (!TextUtils.isEmpty(data!!.memberid)) {
            memberId=data?.memberid!!
        }
        if (!TextUtils.isEmpty(data!!.mobile)) {
            mobile = data?.mobile!!
        }
        if (!TextUtils.isEmpty(data!!.username)) {
            username = data?.username!!
        }
        if (data!!.birthday != 0) {
            birthday = data?.birthday!!
        }
        if (data!!.gender != 0) {
            gender = data?.gender!!
        }
        isHaveInstallmentPwd = data?.isHaveInstallmentPwd!!
        isHaveLoginPwd = data?.isHaveLoginPwd!!
        isLogin = data?.isLogin!!
    }

    var accessToken: String?
        get() = PreferManager()[KEY_TOKEN, ""] as String
        private set(accessToken) {
            PreferManager().put(KEY_TOKEN, accessToken)
        }

    var memberId: String?
        get() = PreferManager()[KEY_TOKEN, ""] as String
        private set(memberId) {
            PreferManager().put(KEY_TOKEN, memberId)
        }

    var mobile: String?
        get() = PreferManager()[KEY_GENDER, ""] as String
        private set(providerShortName) {
            PreferManager().put(
                KEY_GENDER,
                providerShortName
            )
        }

    var gender: Int
        get() = PreferManager()[KEY_GENDER, 0] as Int
        private set(gender) {
            PreferManager().put(
                KEY_GENDER,
                gender
            )
        }

    var username: String
        get() = PreferManager()[KEY_HAS_INSTALL_PWD, ""] as String
        private set(username) {
            PreferManager().put(
                KEY_HAS_INSTALL_PWD,
                username
            )
        }

    var birthday: Int
        get() = PreferManager()[KEY_HAS_LOGIN_PWD, 0] as Int
        private set(birthday) {
            PreferManager().put(KEY_HAS_LOGIN_PWD, birthday)
        }

    var isHaveInstallmentPwd: Boolean
        get() = PreferManager()[KEY_USER_NAME, false] as Boolean
        private set(isHaveInstallmentPwd) {
            PreferManager().put(
                KEY_USER_NAME,
                isHaveInstallmentPwd
            )
        }

    var isHaveLoginPwd: Boolean
        get() = PreferManager()[KEY_LOGIN_STATE, false] as Boolean
        private set(isHaveLoginPwd) {
            PreferManager().put(KEY_LOGIN_STATE, isHaveLoginPwd)
        }

    var isLogin: Boolean
        get() = PreferManager()[KEY_LOGIN_STATE, false] as Boolean
        private set(isLogin) {
            PreferManager().put(KEY_LOGIN_STATE, isLogin)
        }


    val deviceId: String? get() = getDeviceId(context)

    private val currentChannel: String?
        get() {
            val ai: ApplicationInfo
            var channel: String? = null
            try {
                ai = context.packageManager
                    .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
                channel =
                    Objects.requireNonNull(ai.metaData["UMENG_CHANNEL"])
                        .toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return channel
        }

    companion object {

        private const val KEY_TOKEN = "user_token"
        private const val KEY_BIRTHDAY = "user_birthday"
        private const val KEY_GENDER = "user_gender"
        private const val KEY_HAS_INSTALL_PWD = "user_haveInstallmentPwd"
        private const val KEY_HAS_LOGIN_PWD = "user_haveLoginPwd"
        private const val KEY_MEMBER_ID = "user_memberId"
        private const val KEY_MOBILE = "user_mobile"
        private const val KEY_USER_NAME = "user_username"
        private const val KEY_LOGIN_STATE = "user_login_states"

        private var manager: UserManager? = null
        val instance: UserManager?
            get() {
                if (manager == null) {
                    manager = UserManager()
                }
                val fileName = "Users"
                PreferManager().createFile(fileName)
                return manager
            }
    }

}