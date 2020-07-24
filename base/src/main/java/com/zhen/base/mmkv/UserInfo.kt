package com.zhen.base.mmkv

data class UserInfo(
    var accessToken: String? = "",
    var birthday: Int = 0,
    var gender: Int = 0,
    var isHaveInstallmentPwd: Boolean = false,
    var isHaveLoginPwd: Boolean = false,
    var memberid: String? = "",
    var mobile: String? = "",
    var username: String? = "",
    var isLogin: Boolean = false
) {
    companion object {
        private var userBean: UserInfo? = null

        @JvmStatic
        val data: UserInfo?
            get() {
                if (userBean == null) {
                    synchronized(UserInfo::class.java) {
                        if (userBean == null) {
                            userBean =
                                UserInfo()
                        }
                    }
                }
                return userBean
            }
    }
}