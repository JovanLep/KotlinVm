//package com.zhen.base.utils
//
//import android.Manifest
//import android.annotation.SuppressLint
//import android.content.Context
//import androidx.fragment.app.FragmentActivity
//import cn.xiaoneng.activity.ChatActivity
//import cn.xiaoneng.coreapi.ChatParamsBody
//import cn.xiaoneng.uiapi.EPlusFunctionType
//import cn.xiaoneng.uiapi.Ntalker
//import cn.xiaoneng.utils.CoreData
//import cn.xiaoneng.xpush.XPush
//import com.zhen.base.BuildConfig
//import com.zhen.base.R
//
///**
// * Created by zhangxiaoli on 2018/8/1.
// * 小能客服工具类
// */
//class XnChatUtil private constructor() {
//    private var context: Context? = null
//    var level = 0
//    private var initXNSDK = -1
//    private var isXNLogin = false
//
//    private object XiaonengChatUtilsHolder {
//        @SuppressLint("StaticFieldLeak")
//        val INSTANCER = XnChatUtil()
//    }
//
//    fun init(context: Context?) {
//        this.context = context
//    }
//
//    /**
//     * 初始化小能
//     */
//    fun initXNLibrary() {
//        val flag = Ntalker.getBaseInstance().initSDK(
//            context,
//            SITE_ID,
//            SDK_KEY
//        ) // 初始化SDK
//        if (0 == flag) {
//            initXNSDK = flag
//            initXNLibraryOtherParams()
//        } else {
//            LogUtil.e("XN 初始化SDK失败，退出登录，重新初始化，错误码:$flag")
//            xnLogout()
//            initXNSDK = Ntalker.getBaseInstance().initSDK(
//                context,
//                SITE_ID,
//                SDK_KEY
//            ) // 初始化SDK
//            if (0 == initXNSDK) {
//                initXNLibraryOtherParams()
//            } else {
//                LogUtil.e("XN 初始化SDK失败，错误码:$initXNSDK")
//            }
//        }
//    }
//
//    /**
//     * 初始化小能
//     */
//    private fun initXNLibraryOtherParams() {
//        LogUtil.d("XN 初始化SDK成功")
//        xnLogin()
//        val enableDebug = Ntalker.getBaseInstance().enableDebug(BuildConfig.DEBUG) // 选择debug模式
//        if (0 == enableDebug) {
//            LogUtil.d("XN enableDebug 执行成功")
//        } else {
//            LogUtil.d("XN enableDebug 执行失败，错误码:$enableDebug")
//        }
//        Ntalker.getExtendInstance().extensionArea()
//            .addPlusFunction(EPlusFunctionType.DEFAULT_VIDEO) //视频
//        XPush.setNotificationClickToActivity(context, ChatActivity::class.java)
//        XPush.setNotificationShowIconId(context, 0)
//        XPush.setNotificationShowTitleHead(context, "珍品网 ")
//        Ntalker.getExtendInstance().message()
//            .setOnUnreadmsgListener { s: String?, s1: String?, s2: String?, i: Int ->
//                if (null == s || null == s1 || null == s2) {
//                    return@setOnUnreadmsgListener
//                }
//                //发送未读消息信息广播
//                val messageBean = MessageBean()
//                messageBean.setId(s)
//                messageBean.setName(s1)
//                messageBean.setContent(s2)
//                messageBean.setUnReadNum(i)
//                val eventBean = EventBean()
//                eventBean.setEvent(REFRESH_UNREAD_MSG)
//                eventBean.setObject(messageBean)
//                EventBus.getDefault().post(eventBean)
//            }
//    }
//
//    /**
//     * 小能登录
//     */
//    fun xnLogin() {
//        val mSession: Session = Session.get()
//        if (mSession.isLogin() && (!isXNLogin || level == 0)) {
//            LogUtil.d(
//                "XN 登录:memberId:" + mSession.getMemberId()
//                    .toString() + " nickName:" + mSession.getNickName()
//                    .toString() + " level:" + level
//            )
//            val logIn = Ntalker.getBaseInstance()
//                .login(mSession.getMemberId(), mSession.getNickName(), level)
//            if (0 == logIn) {
//                if (level != 0) {
//                    isXNLogin = true
//                }
//                LogUtil.d("XN 登录成功")
//            } else {
//                LogUtil.e("XN 登录失败，错误码:$logIn")
//            }
//        }
//    }
//
//    /**
//     * 小能注销
//     */
//    fun xnLogout() {
//        level = 0
//        isXNLogin = false
//        val logOut = Ntalker.getBaseInstance().logout()
//        if (0 == logOut) {
//            LogUtil.d("XN 注销成功")
//        } else {
//            LogUtil.e("XN 注销失败，错误码:$logOut")
//        }
//    }
//
//    /**
//     * 开启小能客服聊窗
//     *
//     * @param fragmentActivity 上下文环境
//     * @param chatparams       聊天参数体（带子参数,与多个功能有关,不用时传空）
//     */
//    private fun xnStartChat(
//        fragmentActivity: FragmentActivity,
//        chatparams: ChatParamsBody
//    ) {
//        //erp参数, 被用参数,小能只负责经由SDK传到客服端,不做任何处理
//        chatparams.erpParam = ERP_PARAM
//        //聊窗中url链接点击事件的监听
//        chatparams.clickurltoshow_type = CoreData.CLICK_TO_APP_COMPONENT
//        Ntalker.getExtendInstance().message()
//            .setOnMsgUrlClickListener { i: Int, s: String? ->
//                when (i) {
//                    1 -> WebLaunchRoute
//                        .with(fragmentActivity)
//                        .visitUrl(s)
//                        .launch()
//                    else -> {
//                    }
//                }
//            }
//        val PERMISSIONS = arrayOf(
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        )
//        if (PermissionsChecker(fragmentActivity).lacksPermissions(PERMISSIONS)) {
//            PermissionsUtil.requestPermissions(
//                fragmentActivity,
//                ConstantMagicValue.PERMISSION_REQUEST_XIAO_NENG,
//                PERMISSIONS
//            )
//        } else {
//            performXNStartChat(fragmentActivity, chatparams)
//        }
//    }
//
//    /**
//     * 开启小能客服聊窗
//     *
//     * @param fragmentActivity 上下文环境
//     * @param chatparams       聊天参数体（带子参数,与多个功能有关,不用时传空）
//     */
//    private fun performXNStartChat(
//        fragmentActivity: FragmentActivity,
//        chatparams: ChatParamsBody
//    ) {
//        if (initXNSDK == 0 && realXNStartChat(fragmentActivity, chatparams) == 0) {
//            LogUtil.d("XN 打开聊窗成功")
//        } else {
//            LogUtil.w("XN 打开聊窗失败 重试")
//            initXNLibrary()
//            if (initXNSDK == 0) {
//                val flag = realXNStartChat(fragmentActivity, chatparams)
//                if (flag == 0) {
//                    LogUtil.d("XN 打开聊窗成功")
//                } else {
//                    showSnackbar(fragmentActivity, "打开聊窗失败，请重试！错误码:$flag")
//                }
//            } else {
//                showSnackbar(fragmentActivity, "初始化失败，请重试！错误码:$initXNSDK")
//            }
//        }
//    }
//
//    /**
//     * 重新开启小能客服聊窗
//     *
//     * @param context    上下文环境
//     * @param chatparams 聊天参数体（带子参数,与多个功能有关,不用时传空）
//     * @return
//     */
//    private fun realXNStartChat(context: Context, chatparams: ChatParamsBody): Int {
//        return Ntalker.getBaseInstance().startChat(
//            context,
//            SETTING_ID,
//            context.resources.getString(R.string.detail_service_name),
//            chatparams
//        )
//    }
//
//    /**
//     * 开启普通客服聊天
//     *
//     * @param fragmentActivity 上下文环境
//     */
//    fun startCommonChat(fragmentActivity: FragmentActivity) {
//        val chatparams = ChatParamsBody()
//        chatparams.startPageTitle =
//            context!!.resources.getString(R.string.detail_service_name) //咨询发起页标题(必填)
//        chatparams.startPageUrl =
//            CUSTOMER_CONTENT //咨询发起页URL，必须以"http://"开头 （必填）
//        chatparams.itemparams.clientgoodsinfo_type = CoreData.SHOW_GOODS_NO //不展示商品信息
//        xnStartChat(fragmentActivity, chatparams)
//    }
//
//    /**
//     * 开启商品详情客服聊天
//     *
//     * @param fragmentActivity 上下文环境
//     * @param productId        商品ID
//     * @param productName      商品名称
//     * @param productImage     商品图片地址
//     * @param productPrice     商品价格
//     * @param productUrl       商品链接
//     */
//    fun startProductDetialChat(
//        fragmentActivity: FragmentActivity,
//        productId: String?,
//        productName: String?,
//        productImage: String?,
//        productPrice: String?,
//        productUrl: String?
//    ) {
//        val chatparams = ChatParamsBody()
//        // 咨询发起页（专有参数）
//        chatparams.startPageTitle = productName //咨询发起页标题(必填)
//        chatparams.startPageUrl = productUrl //咨询发起页URL，必须以"http://"开头 （必填）
//        // 商品展示（专有参数）
//        chatparams.itemparams.clientgoodsinfo_type =
//            CoreData.SHOW_GOODS_BY_ID //使用商品id方式展示商品信息(设置在客服端是否显示商品)
//        chatparams.itemparams.appgoodsinfo_type =
//            CoreData.SHOW_GOODS_BY_WIDGET //以独立控件方式展示商品信息(设置在SDK端是否显示商品)
//        chatparams.itemparams.clicktoshow_type = CoreData.CLICK_TO_APP_COMPONENT
//        chatparams.itemparams.goods_id = productId //商品id
//        chatparams.itemparams.goods_name = productName //商品名称
//        chatparams.itemparams.goods_price = productPrice //商品价格
//        chatparams.itemparams.goods_image = productImage //商品图片URL
//        chatparams.itemparams.goods_url = productUrl //商品URL（点击跳转）
//        chatparams.itemparams.goods_showurl = productUrl //商品小页面URL(设置以商品URL显示时传入)
//        instance.xnStartChat(fragmentActivity, chatparams)
//    }
//
//    /**
//     * H5页面开启商品详情客服聊天
//     *
//     * @param fragmentActivity 上下文环境
//     */
//    fun startProductDetialChat2(
//        fragmentActivity: FragmentActivity,
//        productDetail: ProductDetail
//    ) {
//        val chatparams = ChatParamsBody()
//        // 咨询发起页（专有参数）
//        chatparams.startPageTitle = productDetail.getProductName() //咨询发起页标题(必填)
//        chatparams.startPageUrl = productDetail.getProductUrl() //咨询发起页URL，必须以"http://"开头 （必填）
//        // 商品展示（专有参数）
//        chatparams.itemparams.clientgoodsinfo_type =
//            CoreData.SHOW_GOODS_BY_ID //使用商品id方式展示商品信息(设置在客服端是否显示商品)
//        chatparams.itemparams.appgoodsinfo_type =
//            CoreData.SHOW_GOODS_BY_WIDGET //以独立控件方式展示商品信息(设置在SDK端是否显示商品)
//        chatparams.itemparams.clicktoshow_type = CoreData.CLICK_TO_APP_COMPONENT
//        chatparams.itemparams.goods_id = productDetail.getProductId() //商品id
//        chatparams.itemparams.goods_name = productDetail.getProductName() //商品名称
//        chatparams.itemparams.goods_price = productDetail.getProductPrice() //商品价格
//        chatparams.itemparams.goods_image = productDetail.getProductImage() //商品图片URL
//        chatparams.itemparams.goods_url = productDetail.getProductUrl() //商品URL（点击跳转）
//        chatparams.itemparams.goods_showurl =
//            productDetail.getProductUrl() //商品小页面URL(设置以商品URL显示时传入)
//        chatparams.itemparams.itemparam = GsonUtil.objectToJson(productDetail)
//        instance.xnStartChat(fragmentActivity, chatparams)
//    }
//
//    companion object {
//        /**
//         * 用于小能客服  // 企业id   // 企业key   // 客服组id
//         */
//        private const val SITE_ID = "kf_9999"
//        private const val SDK_KEY = "6EDD5410-68F8-ECC8-946D-BA5180730BE6"
//        private const val SETTING_ID = "kf_9999_1375345342615"
//        private const val ERP_PARAM = ""
//        private const val CUSTOMER_CONTENT = "https://m.zhen.com"
//
//        /**
//         * 获取XiaonengChatUtils实例 ,单例模式
//         */
//        val instance: XnChatUtil
//            get() = XiaonengChatUtilsHolder.INSTANCER
//    }
//}