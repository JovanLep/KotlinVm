package com.pcl.mvvm.common

object Constant {

        /**
         * 快捷登录flag
         */
        const val FLAG_INTENT_PPT = "intent_ppt"

        /**
         * 快捷注册flag
         */
        const val FLAG_INTENT_QUICK_REGISTER = "intent_ppt_quick_register"

        /**
         * 登录时 未注册
         */
        const val FLAG_LOGIN_BUT_NOT_REGISTER = "intent_login_but_not_register"

        /**
         * 忘记密码flag
         */
        const val FLAG_FORGET_PASSWORD = "intent_forget_password"

        /**
         * intent 键名
         */
        const val INTENT_WALLET_BALANCE_PRICE = "intent_wallet_balance_price"
        const val INTENT_WALLET_COUPONS = "intent_wallet_coupons"
        const val INTENT_WALLET_ALLOWANCE = "intent_wallet_allowance"
        const val INTENT_WALLET_ALLOWANCE_URL = "intent_wallet_allowance_url"
        const val INTENT_WALLET_GIFT_PRICE = "intent_wallet_gift_price"
        const val INTENT_WALLET_ZPBEAN_PRICE = "intent_wallet_zpBean_price"
        const val INTENT_WALLET_ZPBEAN_URL = "intent_wallet_zpBean_url"
        const val INTENT_WALLET_ZPBEAN_HELP_URL = "intent_wallet_zpBean_help_url"
        const val INTENT_WALLET_SCORE_PRICE = "intent_wallet_score_price"

        /**
         * 刷新地址列表
         */
        const val REFRESH_ADDRESS_LIST = 0x2001

        /**
         * 刷新地址信息
         */
        const val REFRESH_ADDRESS_INFO = 0x2002
        const val REFRESH_INVOICE_INFO = 0x2003
        const val REFRESH_CODE_TIPS = 0x2004
        const val REFRESH_CODE_TIME = 0x2005
        const val REFRESH_CODE_MOBILE = 0x2006

        /**
         * 登录页面刷新
         */
        const val REFRESH_CODE_TO_LOGIN = 0x2007

        /**
         * 刷新订单结算
         */
        const val REFRESH_ORDER_CONFIRM = 0x2008

        /**
         * 刷新未读消息个数
         */
        const val REFRESH_MESSAGE_NUM = 0x2009

        /**
         * 刷新首页秒杀
         */
        const val REFRESH_HOME_PREFERENCE = 0x2010

        /**
         * 刷新详情页按钮
         */
        const val REFRESH_BUY_BTN_DATE = 0x2011

        /**
         * 刷新全部订单列表
         */
        const val REFRESH_ORDER_LIST_ALL = 0x2012

        /**
         * 刷新待付款订单列表
         */
        const val REFRESH_ORDER_LIST_TO_PAY = 0x2013

        /**
         * 刷新待收货订单列表
         */
        const val REFRESH_ORDER_LIST_TO_RECEIVE = 0x2014

        /**
         * 刷新已完成订单列表
         */
        const val REFRESH_ORDER_LIST_COMPLETE = 0x2015

        /**
         * 刷新待评价订单列表
         */
        const val REFRESH_ORDER_LIST_TO_EVALUATE = 0x2016

        /**
         * 刷新订单详情
         */
        const val REFRESH_ORDER_DETAIL = 0x2017

        /**
         * 刷新刷新申请进度列表
         */
        const val REFRESH_REFUNDINNG_LIST = 0x2018

        /**
         * 刷新售后申请列表
         */
        const val REFRESH_REFUNDABLE_LIST = 0x2019

        /**
         * 刷新选择退换货列表
         */
        const val REFRESH_REFUND_CHOICE = 0x2020

        /**
         * 刷新退换货申请进度详情
         */
        const val REFRESH_REFUNDINNG_DETAIL = 0x2021

        /**
         * 刷新评价商品列表
         */
        const val REFRESH_EVALUATE_PRODUCT_LIST = 0x2022

        /**
         * 刷新未读消息
         */
        const val REFRESH_UNREAD_MSG = 0x2023

        /**
         * 刷新购物车数量
         */
        const val REFRESH_SHOPPING_CART_NUMBER = 0x2024

        /**
         * 重置购物车
         */
        const val RESET_SHOPPING_CART = 0x2025

        /**
         * 刷新购物车
         */
        const val REFRESH_SHOPPING_CART = 0x2026

        /**
         * 刷新发票商品明细列表
         */
        const val REFRESH_INVOICE_DETAIL_LIST = 0x2027

        /**
         * 刷新发票状态
         */
        const val REFRESH_INVOICE_STATE = 0x2028

        /**
         * 刷新优惠券选择结果
         */
        const val REFRESH_COUPON_CHOOSE = 0x2029

        /**
         * 刷新优惠券数量显示
         */
        const val REFRESH_COUPON_COUNT = 0x2030

        /**
         * 刷新订单结算不清除地址信息
         */
        const val REFRESH_ORDER_CONFIRM_WITHOUT_ADDRESS = 0x2031

        /**
         * 刷新首页
         */
        const val REFRESH_HOME_DATA = 0x2032

        /**
         * 缓存视频结果
         */
        const val VIDEO_CAHE_RESULT = 0x3001

        /**
         * 商品详情关闭视频
         */
        const val VIDEO_CLOSE_PRODUCT_DETAIL = 0x3002

        /**
         * 开始启动页动画
         */
        const val START_WELCOME_ANIMATION = 0x4001

        /**
         * 去首页
         */
        const val GO_TO_MAIN = 0x4002

        /**
         * 初始化广告页
         */
        const val LOAD_WELCOME_DATA = 0x4003

        /**
         * 去引导页
         */
        const val GO_TO_GUIDE = 0x4004

        /**
         * 显示秒杀引导
         */
        const val SHOW_GUIDE_QUICK_LIST = 0x4005

        /**
         * 显示秒杀筛选
         */
        const val SHOW_GUIDE_QUICK_CHOOSE = 0x4006

        /**
         * 增值税发票滚动到顶部
         */
        const val INVOICE_INFO_VAT_TOP = 0x4007

        /**
         * 改变发票商品明细列表选中索引
         */
        const val CHANGE_INVOICE_DETAIL_INDEX = 0x4008

        /**
         * 重拍
         */
        const val RESTART_CAMERA = 0x4009

        /**
         * 子列表更新秒杀列表数据
         */
        const val UPDATE_QUICK_BUY_DATA = 0x4010

        /**
         * 根据订单详情刷新订单列表
         */
        const val UPDATE_ORDER_LIST_DATA = 0x4011
        const val START_CODE_TIME_SERVICE = 0x5001
        const val STOP_CODE_TIME_SERVICE = 0x5002
        const val START_RED_RAIN = 0x5003
        const val RED_RAIN_OVER = 0x5004
        const val REFRESH_CURRENT_TIME = 0x5005
        const val REFRESH_RED_RAIN = 0x5006
        const val START_RED_RAIN_SERVICE = 0x5007
        const val RESTART_RED_RAIN_SERVICE = 0x5008
        const val STOP_RED_RAIN_SERVICE = 0x5009

        /**
         * 启动页面权限请求
         */
        const val PERMISSION_REQUEST_WELCOME = 0x7001

        /**
         * 客服页面权限请求
         */
        const val PERMISSION_REQUEST_XIAO_NENG = 0x7002

        /**
         * 获取联系人权限请求
         */
        const val PERMISSION_REQUEST_CONTACT = 0x7003

        /**
         * 打电话权限请求
         */
        const val PERMISSION_CALL_PHONE = 0x7004

        /**
         * 拍照权限请求
         */
        const val PERMISSION_CAMERA = 0x7005

        /**
         * 相册权限请求
         */
        const val PERMISSION_IMAGE = 0x7006

        /**
         * 修改头像权限请求
         */
        const val PERMISSION_REQUEST_AVATER = 0x7007

        /**
         * 推荐商品头部样式
         */
        const val TYPE_RECOMMEND_PRODUCT_HEADER = 0x8000

        /**
         * 推荐商品样式
         */
        const val TYPE_RECOMMEND_PRODUCT = 0x80001

        /**
         * 民生分期页面标志
         */
        const val PAGE_MS_INS = 0x90000

        /**
         * 支付结果页面标志
         */
        const val PAGE_MS_INS_PAY_RESULT = 0x90001

        /**
         * 刷新民生未支付成功后的支付方式页面
         */
        const val PAGE_MS_INS_PAY_REFRESH = 0x90002

        /**
         * 魔法值 Int 值
         */
        const val NO_0 = 0
        const val NO_1 = 1
        const val NO_2 = 2
        const val NO_3 = 3
        const val NO_4 = 4
        const val NO_5 = 5
        const val NO_6 = 6
        const val NO_100 = 100
        const val NO_101 = 101
        const val NO_115 = 115
        const val NO_999 = 999
        const val NO_1000 = 1000

        /**
         * 魔法值 String 值
         */
        const val STR_MULTIPLY = "*"

        /**
         * 0-正式环境
         */
        const val NET_RELEASE = 0

        /**
         * 1-测试环境
         */
        const val NET_DEBUG = 1

        /**
         * 2-沙箱环境
         */
        const val NET_SAND_BOX = 2

        /**
         * 支付类型-微信
         */
        const val PAY_TYPE_WX = 1

        /**
         * 支付类型-支付宝
         */
        const val PAY_TYPE_AIPAY = 2

        /**
         * 支付类型-银联
         */
        const val PAY_TYPE_UNIO = 3

        /**
         * 购物车活动商品组
         */
        const val SHOPPING_CART_GROUP_ACTIVITY = 3

        /**
         * 购物车换购商品组
         */
        const val SHOPPING_CART_GROUP_CHANGE = 4

        /**
         * 购物车非活动商品组
         */
        const val SHOPPING_CART_GROUP_NO_ACTIVITY = 5

        /**
         * 购物车失效商品组
         */
        const val SHOPPING_CART_GROUP_DISABLE = 6

        /**
         * 购物车购物津贴组
         */
        const val SHOPPING_CART_GROUP_BOUNTY = 7
    }


