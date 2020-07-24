//package com.zhen.base.web;
//
//import android.content.Context;
//import android.content.Intent;
//import android.text.TextUtils;
//
//
///**
//
// * @author Administrator
// */
//public final class WebLaunchRoute {
//
//    public static LaunchManager with(Context context) {
//        return new LaunchManager(context);
//    }
//
//    public static final class LaunchManager {
//        private Context context;
//        private String title;//标题
//        private String visitUrl;//H5加载链接
//        private String shareUrl;//H5分享链接
//        private String helpUrl;//H5帮助链接
//        private boolean isTitleBarGone = false;
//        private boolean isTransparentFrom = false;
//        private int fromId;
//        private int flags = -1;
//        private WebViewHeaders webViewHeaders;
//
//        private LaunchManager(Context context) {
//            this.context = context;
//        }
//
//        public LaunchManager title(String title) {
//            this.title = title;
//            return this;
//        }
//
//        public LaunchManager visitUrl(String visitUrl) {
//            this.visitUrl = visitUrl;
//            return this;
//        }
//
//        public LaunchManager shareUrl(String shareUrl) {
//            this.shareUrl = shareUrl;
//            return this;
//        }
//
//        public LaunchManager helpUrl(String helpUrl) {
//            this.helpUrl = helpUrl;
//            return this;
//        }
//
//        public LaunchManager isTitleBarGone(boolean isTitleBarGone) {
//            this.isTitleBarGone = isTitleBarGone;
//            return this;
//        }
//
//        public LaunchManager isTransparentFrom(boolean isTransparentFrom) {
//            this.isTransparentFrom = isTransparentFrom;
//            return this;
//        }
//
//        public LaunchManager fromId(int fromId) {
//            this.fromId = fromId;
//            return this;
//        }
//
//        public LaunchManager flags(int flags) {
//            this.flags = flags;
//            return this;
//        }
//
//        public LaunchManager webViewHeaders(WebViewHeaders webViewHeaders) {
//            this.webViewHeaders = webViewHeaders;
//            return this;
//        }
//
//        public Intent getIntent() {
//            if (context == null || TextUtils.isEmpty(visitUrl)) {
//                return null;
//            }
//            return WebContentActivity.getWebIntent(context, title, visitUrl, shareUrl, isTitleBarGone, isTransparentFrom, fromId, flags, helpUrl, webViewHeaders);
//        }
//
//        public void launch() {
//            launchForResult(-1);
//        }
//
//        public void launchForResult(int requestCode) {
//            if (context == null || TextUtils.isEmpty(visitUrl)) {
//                return;
//            }
//            WebContentActivity.launchForResult(context, title, visitUrl, shareUrl, isTitleBarGone, isTransparentFrom, fromId, requestCode, flags, helpUrl, webViewHeaders);
//        }
//    }
//}