//package com.zhen.base.web;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.net.http.SslError;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.webkit.CookieManager;
//import android.webkit.DownloadListener;
//import android.webkit.JavascriptInterface;
//import android.webkit.SslErrorHandler;
//import android.webkit.WebChromeClient;
//import android.webkit.WebResourceError;
//import android.webkit.WebResourceRequest;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.FrameLayout;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.networkbench.agent.impl.instrumentation.NBSWebChromeClient;
//import com.umeng.socialize.media.UMImage;
//import com.zhenpin.luxurystore.ActivityStackManager;
//import com.zhenpin.luxurystore.BuildConfig;
//import com.zhenpin.luxurystore.R;
//import com.zhenpin.luxurystore.Session;
//import com.zhenpin.luxurystore.assistor.ZpsAssistor;
//import com.zhenpin.luxurystore.assistor.ZpsConstants;
//import com.zhenpin.luxurystore.beans.EventBean;
//import com.zhenpin.luxurystore.beans.SearchProductParams;
//import com.zhenpin.luxurystore.main.base.CommonSimpleListAdapter;
//import com.zhenpin.luxurystore.main.order.activity.OrderDetailActivity;
//import com.zhenpin.luxurystore.main.order.activity.OrderListActivity;
//import com.zhenpin.luxurystore.main.personal.activity.FeedbackActivity;
//import com.zhenpin.luxurystore.main.personal.activity.ScoreActivity;
//import com.zhenpin.luxurystore.main.personal.activity.SelfInfoActivity;
//import com.zhenpin.luxurystore.main.ppt.activity.BindMobileStep1Activity;
//import com.zhenpin.luxurystore.main.ppt.activity.LoginActivity;
//import com.zhenpin.luxurystore.main.product.activity.GroupsDetailActivity;
//import com.zhenpin.luxurystore.main.product.activity.ProductDetailActivity;
//import com.zhenpin.luxurystore.main.search.activity.ProductListRoute;
//import com.zhenpin.luxurystore.main.shopcart.activity.ShopCartActivity;
//import com.zhenpin.luxurystore.net.ZpLuxuryApi;
//import com.zhenpin.luxurystore.net.ZpNetSubscriber;
//import com.zhenpin.luxurystore.utils.DialogUtil;
//import com.zhenpin.luxurystore.utils.GsonUtil;
//import com.zhenpin.luxurystore.utils.LogUtil;
//import com.zhenpin.luxurystore.utils.SharePreUtil;
//import com.zhenpin.luxurystore.view.CustomWebView;
//import com.zhenpin.luxurystore.view.dialog.BaseDialog;
//import com.zhenpin.luxurystore.view.ptr.StatusView;
//import com.zhenpin.luxurystore.wxapi.WXPayEntryActivity;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.io.UnsupportedEncodingException;
//import java.net.HttpCookie;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import static com.zhenpin.luxurystore.utils.CommonUtil.isNetworkAvailable;
//import static com.zhenpin.luxurystore.utils.ConstantMagicValue.PAGE_MS_INS;
//import static com.zhenpin.luxurystore.utils.ConstantMagicValue.PAGE_MS_INS_PAY_REFRESH;
//import static com.zhenpin.luxurystore.utils.ConstantMagicValue.PAGE_MS_INS_PAY_RESULT;
//import static com.zhenpin.luxurystore.utils.SharePreConstants.HISTORY_WORDS;
//import static com.zhenpin.luxurystore.utils.SkipUtil.goToHome;
//import static com.zhenpin.luxurystore.utils.SnackbarUtil.showSnackbar;
//import static com.zhenpin.luxurystore.utils.StringUtil.copyOrderId;
//
//public class WebContentView extends FrameLayout {
//    private Activity mActivity;
//    private CustomWebView mWebView;
//    private StatusView mStatusView;
//    private WebLaunchParams webLaunchParams;
//    private WebViewActionListener webViewActionListener;
//    private WebViewApiRequestListener webViewApiRequestListener;
//    private boolean mIsWebViewAvailable = false;
//    private volatile boolean isReceivedError = false;
//    private String lastUrl = "";
//    private String shareTitle;
//    private String shareUrl;
//    private String shareIcon;
//    private String shareContent = "";
//    private String fromTxt;
//    public static final int COMMON_WEB_TOLOGIN_RESULT = 0x028;
//    private static final int LOAD_SUCCESS_WEBVIEW = 1;
//    private static final int LOAD_ERROR_WEBVIEW = 2;
//    private static final int LOAD_UPDATE_SHARE_INFO = 3;
//    private BaseDialog baseDialog;
//
//    public WebContentView(Context context) {
//        this(context, null);
//    }
//
//    public WebContentView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public WebContentView(Context context, AttributeSet attrs, int defStyleAttr) {
//        this(context, attrs, defStyleAttr, 0);
//    }
//
//    public WebContentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        LayoutInflater.from(getContext()).inflate(R.layout.layout_webview_content, this);
//        initViews();
//        initEvents();
//    }
//
//    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
//    protected void initViews() {
//        mWebView = findViewById(R.id.webview);
//        mStatusView = findViewById(R.id.status_view);
//        mStatusView.errorBtnName(getResources().getString(R.string.common_reload))
//                .errorBtnClickListener(v -> loadData());
//        mIsWebViewAvailable = true;
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setAllowFileAccess(true);
//        webSettings.setSupportMultipleWindows(true);
//        webSettings.setSupportZoom(true); // 可以缩放
//        webSettings.setBuiltInZoomControls(true); // 显示放大缩小 controler
//        webSettings.setDisplayZoomControls(false);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setPluginState(WebSettings.PluginState.ON);
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSettings.setLoadsImagesAutomatically(true);
//        webSettings.setTextZoom(100);
//        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        String userAgentString = webSettings.getUserAgentString();
//        webSettings.setUserAgentString(userAgentString + " ZhenpinAPP" + "/" + BuildConfig.VERSION_NAME);
//        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        mWebView.addJavascriptInterface(new JsToJava(), "adObject");
//        mWebView.setDownloadListener(new MyWebViewDownLoadListener());
//    }
//
//    protected void initEvents() {
//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.startsWith("zpmall")) {
//                    if ("zpmall://home".equals(url)) {
//                        // 去首页
//                        goToHome(getContext(), 0);
//                    } else if (url.startsWith("zpmallpid")) {
//                        // 去商品详情页
//                        try {
//                            toProductDetail(url, webLaunchParams.getFromId(), URLEncoder.encode(mWebView.getUrl(), "utf-8"));
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//                    } else if (url.startsWith("zpmall://special")) {
//                        try {
//                            int lastIndexOf = url.indexOf("?");
//                            String substring = url.substring(lastIndexOf + 1);
//                            String[] split = substring.split('\\' + "|");
//                            if (null != split[0]) {
//                                int lastIndexOf2 = split[0].indexOf("=");
//                                url = split[0].substring(lastIndexOf2 + 1);
//                                String decode = URLDecoder.decode(url, "utf-8");
//                                view.loadUrl(decode);
//                                lastUrl = url;
//                            }
//                            if (null != split[1]) {
//                                int lastIndexOf2 = split[1].indexOf("=");
//                                String title = split[1].substring(lastIndexOf2 + 1);
//                                if (!TextUtils.isEmpty(title)) {
//                                    String decode = URLDecoder.decode(title, "utf-8");
//                                    setShareContent(decode);
//                                    // setTitleText(decode);
//                                    // txtTitle.setText(decode);
//                                }
//                            }
//                            if (null != split[2]) {
//                                int lastIndexOf2 = split[2].indexOf("=");
//                                String shareUrl = split[2].substring(lastIndexOf2 + 1);
//                                String decode = URLDecoder.decode(shareUrl, "utf-8");
//                                setShareUrl(decode);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    // 打开分享面板
//                    else if (url.startsWith("zpmall://share")) {
//                        String strUrl = "Url";
//                        String strTitle = "Title";
//                        String strContent = "Description";
//                        try {
//                            Map<String, String> map = new HashMap<>(16);
//                            final String strTemp = url.substring(url.indexOf("?") + 1);
//                            String[] split = strTemp.split('\\' + "|");
//                            for (String aSplit : split) {
//                                String key = aSplit.substring(0, aSplit.indexOf("="));
//                                String value = aSplit.substring(aSplit.indexOf("=") + 1);
//                                map.put(key, URLDecoder.decode(value, "utf-8"));
//                            }
//                            UMImage umImage = new UMImage(getActivity(), ZpLuxuryApi.API_HOST_REDSHAREIMG + "?id=" + UUID.randomUUID().toString());
//                            webViewActionListener.openSharePanel(false, map.get(strUrl), umImage, map.get(strTitle), map.get(strContent));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    // 直接下单
//                    else if (url.startsWith("zpmall://order")) {
//                        try {
//                            fromTxt = URLEncoder.encode(mWebView.getUrl(), "utf-8");
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//                        if (webViewActionListener != null) {
//                            webViewActionListener.buyAtOnce(url, webLaunchParams.getFromId(), fromTxt);
//                        }
//                    }
//                    // 添加购物车
//                    else if (url.startsWith("zpmall://addcart")) {
//                        try {
//                            fromTxt = URLEncoder.encode(mWebView.getUrl(), "utf-8");
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//                        int indexOf = url.indexOf("=");
//                        String specId = url.substring(indexOf + 1);
//                        androidAddToShopBagOnUiThread(specId);
//                    }
//                    // 跳转购物车
//                    else if (url.startsWith("zpmall://toshopcart")) {
//                        startActivity(new Intent(getActivity(), ShopCartActivity.class));
//                    }
//                }
//                // 跳转至登录界面
//                else if (url.startsWith("tologin")) {
//                    toLogin(false);
//                }
//                // 拨打电话
//                else if (url.startsWith("tel:")) {
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
//                    startActivity(intent);
//                }
//                // 跳转原生积分
//                else if (url.contains("y=native_score")) {
//                    if (!Session.get().isLogin()) {
//                        toLogin(false);
//                    } else {
//                        Intent shakeIntent = new Intent();
//                        shakeIntent.setClass(getActivity(), ScoreActivity.class);
//                        startActivity(shakeIntent);
//                    }
//                }
//                // 去下一页
//                else {
//                    return toNextUrlPage(view, url);
//                }
//                return true;
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                LogUtil.i(WebContentView.class.getSimpleName(), "onPageStarted:" + view.getTitle() + " " + url);
//                syncCookie(url);
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                LogUtil.i(WebContentView.class.getSimpleName(), "onPageFinished:" + view.getTitle() + " " + url);
//                updateTitleAndShareInfo(view, view.getTitle(), url);
//                handler.sendEmptyMessage(LOAD_SUCCESS_WEBVIEW);
//            }
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                isReceivedError = true;
//                handler.sendEmptyMessage(LOAD_ERROR_WEBVIEW);
//            }
//
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                handler.proceed();
//            }
//        });
//        mWebView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
//                LogUtil.i(WebContentView.class.getSimpleName(), "onReceivedTitle:" + title + " " + view.getUrl());
//                updateTitleAndShareInfo(view, title, view.getUrl());
//            }
//
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                NBSWebChromeClient.initJSMonitor(view, newProgress);
//                super.onProgressChanged(view, newProgress);
//                if (mStatusView != null) {
//                    if (newProgress == 100) {
//                        dismissProgressView();
//                    } else {
//                        showProgressView();
//                    }
//                }
//            }
//        });
//    }
//
//    public void updateTitleAndShareInfo(WebView webView, String cTitle, String url) {
//        if (webView == null) {
//            return;
//        }
//        String title = webLaunchParams.getTitleTxt();
//        if (webView.canGoBack()) {
//            title = TextUtils.isEmpty(cTitle) ? title : cTitle;
//        } else {
//            title = TextUtils.isEmpty(title) ? cTitle : title;
//        }
//        String content = null;
//        CharSequence contentDescription = webView.getContentDescription();
//        if (contentDescription != null) {
//            content = contentDescription.toString();
//        }
//        setShareTitle(title);
//        setShareContent(TextUtils.isEmpty(content) ? title : content);
//        setShareUrl(url);
//        handler.sendEmptyMessage(LOAD_UPDATE_SHARE_INFO);
//        if (webViewActionListener != null) {
//            if (TextUtils.isEmpty(title) || "Document".equals(title) || title.contains("http://") || title.contains("https://")) {
//                webViewActionListener.updateTitle(webView, webLaunchParams.getTitleTxt());
//            } else {
//                webViewActionListener.updateTitle(webView, title);
//            }
//        }
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == COMMON_WEB_TOLOGIN_RESULT) {
//            if (resultCode == LoginActivity.LOGIN_SUCCESS) {
//                if (!isNetworkAvailable(getContext())) {
//                    onLoadError();
//                    return;
//                }
//                loadData();
//            } else if (resultCode == LoginActivity.LOGIN_UNLOGIN) {
//                if (!isNetworkAvailable(getContext())) {
//                    onLoadError();
//                    return;
//                }
//                if (data != null && data.getExtras() != null) {
//                    boolean isBackFinish = data.getExtras().getBoolean("isBackFinish");
//                    if (isBackFinish) {
//                        getActivity().finish();
//                    }
//                }
//            }
//        }
//    }
//
//    protected void onLoadError() {
//        if (mStatusView != null) {
//            if (mWebView != null) {
//                mWebView.loadUrl("javascript:document.body.innerHTML=\"" + "" + "\"");
//            }
//            if (!isNetworkAvailable(getContext())) {
//                mStatusView.showErrorPage(true, ZpNetSubscriber.CONNECT_ERROR, ZpNetSubscriber.ERROR_MSG_CONNECT);
//            } else {
//                mStatusView.showErrorPage(false, ZpNetSubscriber.BUSINESS_ERROR, ZpNetSubscriber.ERROR_MSG_BUSINESS);
//            }
//        }
//    }
//
//    private void showProgressView() {
//        if (mStatusView != null) {
//            mStatusView.showProgressView();
//        }
//    }
//
//    private void dismissProgressView() {
//        if (mStatusView != null && !isReceivedError) {
//            mStatusView.dismissProgressView();
//        }
//    }
//
//    public void loadData() {
//        if (mWebView == null) {
//            return;
//        }
//        if (isNetworkAvailable(getContext())) {
//            String cUrl = mWebView.getUrl();
//            String visitUrl = webLaunchParams == null ? null : webLaunchParams.getVisitUrl();
//            if (TextUtils.isEmpty(cUrl)) {
//                if (!TextUtils.isEmpty(visitUrl)) {
//                    loadUrlAndResetCookieIfNeed(webLaunchParams.getVisitUrl());
//                } else {
//                    isReceivedError = true;
//                    handler.sendEmptyMessage(LOAD_ERROR_WEBVIEW);
//                }
//            } else {
//                loadUrlAndResetCookieIfNeed(cUrl);
//            }
//        } else {
//            isReceivedError = true;
//            handler.sendEmptyMessage(LOAD_ERROR_WEBVIEW);
//        }
//    }
//
//    public void loadUrlAndResetCookieIfNeed(final String url) {
//        if (url == null) {
//            return;
//        }
//        if (!isNeedSyncCookie(url)) {
//            if (webLaunchParams.getWebViewHeaders() != null && webLaunchParams.getWebViewHeaders().getHeaders() != null && !webLaunchParams.getWebViewHeaders().getHeaders().isEmpty()) {
//                loadUrl(url, webLaunchParams.getWebViewHeaders().getHeaders());
//            } else {
//                loadUrl(url);
//            }
//            return;
//        }
//        if (!mWebView.canGoBack()) {
//            CookieManager cookieManager = CookieManager.getInstance();
//            cookieManager.setAcceptCookie(true);
//            // 清除老的cookie
//            cookieManager.removeAllCookies(null);
//            cookieManager.removeSessionCookies(null);
//            LogUtil.i(WebContentView.class.getSimpleName(), "reset cookie");
//            handler.postDelayed(() -> {
//                cookieManager.flush();
//                LogUtil.i(WebContentView.class.getSimpleName(), "cookieManager=" + cookieManager.getCookie(url));
//                syncCookie(url, cookieManager);
//                loadUrl(url);
//            }, 200);
//        } else {
//            loadUrl(url);
//        }
//    }
//
//    private void loadUrl(String url) {
//        if (TextUtils.isEmpty(url)) {
//            return;
//        }
//        if (url.equals(mWebView.getUrl())) {
//            mWebView.reload();
//            return;
//        }
//        isReceivedError = false;
//        showProgressView();
//        mWebView.loadUrl(url);
//    }
//
//    private void loadUrl(String url, Map<String, String> headers) {
//        if (TextUtils.isEmpty(url)) {
//            return;
//        }
//        if (url.equals(mWebView.getUrl())) {
//            mWebView.reload();
//            return;
//        }
//        isReceivedError = false;
//        showProgressView();
//        mWebView.loadUrl(url, headers);
//    }
//
//    private boolean isNeedSyncCookie(String url) {
//        return url.contains(".zhen.com") || url.contains(".zhenpin.com");
//    }
//
//    private void syncCookie(String url) {
//        syncCookie(url, null);
//    }
//
//    private void syncCookie(String url, CookieManager cookieManager) {
//        if (!isNeedSyncCookie(url)) {
//            return;
//        }
//        if (cookieManager == null) {
//            cookieManager = CookieManager.getInstance();
//            cookieManager.setAcceptCookie(true);
//        }
//        HttpCookie httpCookie = new HttpCookie("cookies", "androidcookies");
//        httpCookie.setVersion(1);
//        httpCookie.setPath("/");
//        if (url.contains(".zhenpin.com")) {
//            httpCookie.setDomain(".zhenpin.com");
//        } else if (url.contains(".zhen.com")) {
//            httpCookie.setDomain(".zhen.com");
//        }
//        String cookieString = "[version: " + httpCookie.getVersion() + "]" +
//                "[name: " + httpCookie.getName() + "]" +
//                "[value: " + httpCookie.getValue() + "]" +
//                "[domain: " + httpCookie.getDomain() + "]" +
//                "[path: " + httpCookie.getPath() + "]" +
//                "[expiry: null]";
//        cookieManager.setCookie(url, cookieString);
//        Session mSession = Session.get();
//        try {
//            String utf8Name = URLEncoder.encode(mSession.getUserName(), "utf-8");
//            cookieManager.setCookie(url, "zpusername=" + utf8Name);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        cookieManager.setCookie(url, "zpmemberid=" + mSession.getMemberId());
//        cookieManager.setCookie(url, "zptoken=" + mSession.getToken());
//        cookieManager.setCookie(url, "deviceid=" + mSession.getDeviceId());
//        cookieManager.setCookie(url, "version=" + BuildConfig.VERSION_NAME);
//        LogUtil.i(WebContentView.class.getSimpleName(), "cookieManager=" + cookieManager.getCookie(url));
//        cookieManager.flush();
//    }
//
//    /**
//     * 跳转至商品详情页
//     *
//     * @param url
//     */
//    protected void toProductDetail(String url, int fromid, String fromtxt) {
//        LogUtil.i(WebContentView.class.getSimpleName(), "fromtxt==" + fromtxt);
//        if (url.contains("quick")) {
//            int indexOf = url.indexOf("?");
//            String substring = url.substring(indexOf + 1);
//            String[] split = substring.split('\\' + "|");
//            String productId = split[0];
//            String groupId = split[1];
//            Intent inten = new Intent();
//            Bundle bun = new Bundle();
//            bun.putString("productId", productId);
//            bun.putString("groupId", groupId);
//            bun.putInt("fromid", fromid);
//            bun.putString("fromtxt", fromtxt);
//            inten.setClass(getActivity(), ProductDetailActivity.class);
//            inten.putExtras(bun);
//            startActivity(inten);
//        } else {
//            String sub = url.substring(url.indexOf("?") + 1);
//            Intent inten = new Intent();
//            Bundle bun = new Bundle();
//            bun.putString("productId", sub);
//            bun.putInt("fromid", fromid);
//            bun.putString("fromtxt", fromtxt);
//            inten.setClass(getActivity(), ProductDetailActivity.class);
//            inten.putExtras(bun);
//            startActivity(inten);
//        }
//    }
//
//    /**
//     * 跳转至登录界面
//     *
//     * @param isBackFinish 若未登录 返回是否退出本页面
//     */
//    protected synchronized void toLogin(boolean isBackFinish) {
//        if (LoginActivity.class.getName().equals(ActivityStackManager.get().getTopActivity().getClass().getName())) {
//            LogUtil.i(WebContentView.class.getSimpleName(), "LoginActivity have active");
//            return;
//        }
//        Session.get().setLogin(false);
//        Intent intent = new Intent(getActivity(), LoginActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("from", "common_tologin_result");
//        bundle.putBoolean("isBackFinish", isBackFinish);
//        bundle.putBoolean("isFromH5", true);
//        intent.putExtras(bundle);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivityForResult(intent, COMMON_WEB_TOLOGIN_RESULT);
//    }
//
//    public void onBackPressed() {
//        if (mWebView == null) {
//            return;
//        }
//        if (webLaunchParams != null && webLaunchParams.getFromId() == PAGE_MS_INS && webLaunchParams.isActivity()) {
//            LogUtil.d(WebContentView.class.getSimpleName(), "民生分期页面");
//            //民生分期页面直接关闭
//            EventBean eventBean = new EventBean();
//            eventBean.setEvent(PAGE_MS_INS_PAY_REFRESH);
//            EventBus.getDefault().post(eventBean);
//            getActivity().finish();
//            return;
//        }
//        if (webLaunchParams != null && webLaunchParams.getFromId() == PAGE_MS_INS_PAY_RESULT && webLaunchParams.isActivity()) {
//            //民生分期支付结果页面返回首页
//            LogUtil.d(WebContentView.class.getSimpleName(), "民生分期支付结果页面");
//            ActivityStackManager.get().killActivity(WXPayEntryActivity.class);
//            getActivity().finish();
//            return;
//        }
//        if (mWebView.canGoBack()) {
//            setShareContent("");
//            mWebView.goBack();
//        } else {
//            if (webLaunchParams != null && webLaunchParams.isActivity()) {
//                getActivity().finish();
//            }
//        }
//    }
//
//    public boolean toNextUrlPage(final WebView view, final String url) {
//        //Android8.0以下的需要返回true 并且需要loadUrl；8.0之后效果相反
//        boolean flag = Build.VERSION.SDK_INT < Build.VERSION_CODES.O;
//        if (url.equals(mWebView.getUrl())) {
//            mWebView.reload();
//            return true;
//        }
//        if (!url.startsWith("http:") && !url.startsWith("https:")) {
//            try {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            return true;
//        }
//        if (webLaunchParams != null && webLaunchParams.isActivity()) {
//            if (flag) {
//                loadUrl(url);
//            }
//        } else {
//            WebLaunchRoute.with(mActivity)
//                    .visitUrl(url)
//                    .shareUrl(webLaunchParams.getShareUrl())
//                    .flags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
//                    .fromId(webLaunchParams.getFromId())
//                    .launch();
//            flag = true;
//        }
//        addAssistorEvent(url, view.getTitle());
//        return flag;
//    }
//
//    private void addAssistorEvent(String url, String title) {
//        String lastUrl = null;
//        String gotoUrl = null;
//        try {
//            lastUrl = URLEncoder.encode(this.lastUrl, "utf-8");
//            gotoUrl = URLEncoder.encode(URLEncoder.encode(url, "utf-8"), "utf-8");
//        } catch (UnsupportedEncodingException e1) {
//            e1.printStackTrace();
//        }
//        HashMap<String, String> map = new HashMap<>(16);
//        if (lastUrl != null) {
//            map.put("url", lastUrl);
//        }
//        map.put("title", title);
//        if (gotoUrl != null) {
//            map.put("goToUrl", gotoUrl);
//        }
//        ZpsAssistor.onEvent(getActivity(), ZpsConstants.AT_H5SKIP, ZpsConstants.V_COMMONWEBCONTENT, map);
//        this.lastUrl = url;
//    }
//
//    public void startActivity(Intent intent) {
//        if (webViewActionListener != null) {
//            webViewActionListener.startActivity(intent);
//        }
//    }
//
//    public void startActivityForResult(Intent intent, int requestCode) {
//        if (webViewActionListener != null) {
//            webViewActionListener.startActivityForResult(intent, requestCode);
//        }
//    }
//
//    public void onPause() {
//        if (mWebView != null) {
//            mWebView.onPause();
//        }
//    }
//
//    public void onResume() {
//        if (mWebView != null) {
//            mWebView.onResume();
//        }
//    }
//
//    public void onDestroyView() {
//        mIsWebViewAvailable = false;
//        handler.removeCallbacksAndMessages(null);
//    }
//
//    public void onDestroy() {
//        destroy();
//    }
//
//    public void destroy() {
//        if (mWebView != null) {
//            mWebView.destroy();
//        }
//    }
//
//    public boolean isWebViewAvailable() {
//        return mIsWebViewAvailable;
//    }
//
//    public Activity getActivity() {
//        return mActivity == null ? (Activity) getContext() : mActivity;
//    }
//
//    public String getShareContent() {
//        return shareContent;
//    }
//
//    public void setShareContent(String shareContent) {
//        this.shareContent = shareContent;
//    }
//
//    public String getShareTitle() {
//        return shareTitle;
//    }
//
//    public void setShareTitle(String shareTitle) {
//        this.shareTitle = shareTitle;
//    }
//
//    public String getShareUrl() {
//        return shareUrl;
//    }
//
//    public void setShareUrl(String shareUrl) {
//        this.shareUrl = shareUrl;
//    }
//
//    public String getShareIcon() {
//        return shareIcon;
//    }
//
//    public void setShareIcon(String shareIcon) {
//        this.shareIcon = shareIcon;
//    }
//
//    /**
//     * ！！！！该方法为子线程，注意线程安全！！！！
//     */
//    public class JsToJava {
//
//        /**
//         * 设置分享(兼容3.0)
//         *
//         * @param orderId
//         */
//        @JavascriptInterface
//        public void androidToOrderDetail(String orderId) {
//            androidToOrderDetailOnUiThread(orderId);
//        }
//
//        @JavascriptInterface
//        // 设置分享(兼容3.0)
//        public void androidShareContent(String icon, String url, String title, String content) {
//            androidShareContentOnUiThread(icon, url, title, content);
//        }
//
//        @JavascriptInterface
//        // 设置分享
//        public void androidShareContent(String icon, String url, String title, String content, boolean isShowNow) {
//            androidShareContentOnUiThread(icon, url, title, content, isShowNow);
//        }
//
//        @JavascriptInterface
//        // 页面跳转
//        public void androidViewSkip(int type) {
//            androidViewSkipOnUiThread(type);
//        }
//
//        @JavascriptInterface
//        // 去拼团商品详情
//        public void androidToProductGroupsDetail(String productId, String diffSource, String activityId) {
//            androidToProductGroupsDetailOnUiThread(productId, diffSource, activityId);
//        }
//
//        @JavascriptInterface
//        // 添加到收藏
//        public void androidAddToCollect(String productId) {
//            androidAddToCollectOnUiThread(productId);
//        }
//
//        @JavascriptInterface
//        // 复制到剪切板
//        public void androidCopy(String text) {
//            androidCopyOnUiThread(text);
//        }
//
//        @JavascriptInterface
//        // 添加到购物车
//        public void androidAddToShopBag(String specId) {
//            androidAddToShopBagOnUiThread(specId);
//        }
//
//        // 跳转到商品列表页
//        @JavascriptInterface
//        public void androidToProductList(final int type, final String id, final String title, final String gender) {
//            androidToProductListOnUiThread(type, id, title, gender, null);
//        }
//
//        // 跳转到商品列表页
//        @JavascriptInterface
//        public void androidToProductList(final int type, final String id, final String title, final String gender, final String filterBrand) {
//            androidToProductListOnUiThread(type, id, title, gender, filterBrand);
//        }
//
//        // 跳转到商品列表页
//        @JavascriptInterface
//        public void androidToProductList(final String jsonStr) {
//            androidToProductListOnUiThread(jsonStr);
//        }
//
//        // 跳转到拼团详情页
//        @JavascriptInterface
//        public void androidToGroupsDetail(final String teamId, final String productId) {
//            androidToGroupsDetailOnUiThread(teamId, productId);
//        }
//
//        // 跳转到支付页
//        @JavascriptInterface
//        public void androidToPay(final String orderId, final String ordrtSn, final String needpay, final String returnUrl, final String type) {
//            androidToPayOnUiThread(orderId, ordrtSn, needpay, returnUrl, type);
//        }
//
//        // 跳出当前页到新的h5页面
//        @JavascriptInterface
//        public void androidToNewWebView(final String title, final String htmlUrl, final String shareUrl) {
//            androidToNewWebViewOnUiThread(title, htmlUrl, shareUrl);
//        }
//
//        // 跳到订单列表页面
//        @JavascriptInterface
//        public void androidToOrderList(final int type) {
//            androidToOrderListOnUiThread(type);
//        }
//
//        /**
//         * 弹出Toast
//         *
//         * @param txt
//         */
//        @JavascriptInterface
//        public void androidShowToast(final String txt) {
//            androidShowToastOnUiThread(txt);
//        }
//
//        /**
//         * 弹出Dialog
//         *
//         * @param type        1 通知型（单确定）    2 询问型（确定+取消）    3 选择型（列表）
//         * @param title       类型为1或2时，将消息内容传至title，content传null
//         * @param content     类型为3时，选择参数用“,”隔开
//         * @param requestCode H5端用来区分dialog的回传
//         */
//        @JavascriptInterface
//        public void androidShowDialog(final int type, final String title, final String content, final int requestCode) {
//            androidShowShowDialogOnUiThread(type, title, content, requestCode);
//        }
//
//        /**
//         * 去首页 tab
//         *
//         * @param index
//         */
//        @JavascriptInterface
//        public void androidToMainTab(int index) {
//            androidToMainTabOnUiThread(index);
//        }
//
//        /**
//         * 分期 申请额度 回调
//         *
//         * @param result
//         */
//        @JavascriptInterface
//        public void onZhenApplyResult(int result) {
//            androidOnZhenApplyResultOnUiThread(result);
//        }
//
//        /**
//         * 更改页面标题
//         *
//         * @param title 页面标题
//         */
//        @JavascriptInterface
//        public void changeAppTitle(String title) {
//            if (webViewActionListener != null) {
//                webViewActionListener.updateTitle(mWebView, title);
//            }
//        }
//
//        @JavascriptInterface
//        public void androidOpenBrowser(String url) {
//            openBrowser(mActivity, url);
//        }
//
//        @JavascriptInterface
//        public void isTitleBarShowService(boolean isShow, String json) {
//            if (webViewActionListener != null) {
//                webViewActionListener.isTitleBarShowService(isShow, json);
//            }
//        }
//
//        @JavascriptInterface
//        public void isPayResultPage() {
//            if (webLaunchParams.getFromId() == PAGE_MS_INS) {
//                LogUtil.d(WebContentView.class.getSimpleName(), "isPayResultPage");
//                webLaunchParams.setFromId(PAGE_MS_INS_PAY_RESULT);
//            }
//        }
//    }
//
//    private void androidToProductGroupsDetailOnUiThread(final String productId, final String diffSource, final String activityId) {
//        getActivity().runOnUiThread(() -> {
//            Intent inten = new Intent();
//            Bundle bun = new Bundle();
//            bun.putString("productId", productId);
//            bun.putString("diffSource", diffSource);
//            bun.putString("activityId", activityId);
//            bun.putInt("fromid", webLaunchParams.getFromId());
//            bun.putString("fromtxt", mWebView.getUrl());
//            inten.setClass(getActivity(), ProductDetailActivity.class);
//            inten.putExtras(bun);
//            startActivity(inten);
//        });
//    }
//
//    protected void androidToGroupsDetailOnUiThread(final String teamId, final String productId) {
//        getActivity().runOnUiThread(() -> {
//            Intent inten = new Intent();
//            Bundle bun = new Bundle();
//            bun.putString("productId", productId);
//            bun.putString("teamId", teamId);
//            bun.putInt("fromid", webLaunchParams.getFromId());
//            bun.putString("fromtxt", mWebView.getUrl());
//            inten.setClass(getActivity(), GroupsDetailActivity.class);
//            inten.putExtras(bun);
//            startActivity(inten);
//        });
//    }
//
//    protected void androidToOrderDetailOnUiThread(final String orderId) {
//        getActivity().runOnUiThread(() -> {
//            Intent intent = new Intent();
//            Bundle bd = new Bundle();
//            bd.putString("orderId", orderId);
//            intent.putExtras(bd);
//            intent.setClass(getActivity(), OrderDetailActivity.class);
//            startActivity(intent);
//        });
//    }
//
//    private void androidShowShowDialogOnUiThread(final int type, final String title, final String content, final int requestCode) {
//        getActivity().runOnUiThread(() -> {
//            switch (type) {
//                case 1:
//                    BaseDialog diaAlert = DialogUtil.initTipDialog(getActivity(), title, content, "确认", (dialog, which) -> {
//                        dialog.dismiss();
//                        //回传参数
//                        if (mWebView != null) {
//                            mWebView.loadUrl("javascript:setSure('" + requestCode + "')");
//                        }
//                    });
//                    diaAlert.show();
//                    break;
//                case 2:
//                    //回传参数
//                    baseDialog = DialogUtil.initTipDialog(getActivity(), title, content, "取消", (dialog, which) -> {
//                        baseDialog.dismiss();
//                        //回传参数
//                        if (mWebView != null) {
//                            mWebView.loadUrl("javascript:setCancle('" + requestCode + "')");
//                        }
//                    }, "确认", (dialog, which) -> {
//                        baseDialog.dismiss();
//                        //回传参数
//                        if (mWebView != null) {
//                            mWebView.loadUrl("javascript:setSure('" + requestCode + "')");
//                        }
//                    });
//                    baseDialog.show();
//                    break;
//                case 3:
//                    final List<String> chooseList = new ArrayList<>();
//                    String[] strs = content.split(",");
//                    Collections.addAll(chooseList, strs);
//                    CommonSimpleListAdapter sexAdapter = new CommonSimpleListAdapter(getActivity(), chooseList);
//                    DialogUtil.initListDialog(mActivity, "title", sexAdapter, (parent, view, position, id) -> {
//                        //回传参数
//                        if (mWebView != null) {
//                            mWebView.loadUrl("javascript:setChooseItem('" + position + "','" + requestCode + "')");
//                        }
//                    }).show();
//                    break;
//                default:
//                    break;
//            }
//        });
//    }
//
//    private void androidToPayOnUiThread(final String orderId, final String ordrtSn, final String needpay, final String returnUrl, final String type) {
//        getActivity().runOnUiThread(() -> {
//            Intent payInte = new Intent();
//            Bundle bd = new Bundle();
//            bd.putString("orderId", orderId);
//            bd.putString("orderSn", ordrtSn);
//            bd.putString("needPay", needpay);
//            bd.putString("showType", type);
//            bd.putString("returnUrl", returnUrl);
//            bd.putInt("from", 2);
//            payInte.putExtras(bd);
//            payInte.setClass(getActivity(), WXPayEntryActivity.class);
//            startActivity(payInte);
//        });
//    }
//
//    private void androidShowToastOnUiThread(final String txt) {
//        getActivity().runOnUiThread(() -> showSnackbar(getActivity(), txt));
//    }
//
//    private void androidShareContentOnUiThread(final String icon, final String url, final String title, final String content) {
//        getActivity().runOnUiThread(() -> {
//            if (!TextUtils.isEmpty(icon)) {
//                setShareIcon(icon);
//            } else {
//                setShareIcon(ZpLuxuryApi.API_HOST_SHAREIMG + "?id=" + UUID.randomUUID().toString());
//            }
//            if (!TextUtils.isEmpty(url)) {
//                setShareUrl(url);
//            }
//            if (!TextUtils.isEmpty(title)) {
//                setShareTitle(title);
//            }
//            if (!TextUtils.isEmpty(content)) {
//                setShareContent(content);
//            }
//        });
//    }
//
//    public void androidShareContentOnUiThread(final String icon, final String url, final String title, final String content, final boolean isShowNow) {
//        LogUtil.i(WebContentView.class.getSimpleName(), "androidShareContentOnUiThread:" + "icon=" + icon + "\n" + "url=" + url + "\n"
//                + "title=" + title + "\n" + "content=" + content + "\n" + "isShowNow=" + isShowNow);
//        // 如果不是立即显示，则配置分享内容
//        getActivity().runOnUiThread(() -> {
//            if (!isShowNow) {
//                if (!TextUtils.isEmpty(icon)) {
//                    setShareIcon(icon);
//                } else {
//                    setShareIcon(ZpLuxuryApi.API_HOST_SHAREIMG + "?id=" + UUID.randomUUID().toString());
//                }
//                if (!TextUtils.isEmpty(url)) {
//                    setShareUrl(url);
//                }
//                if (!TextUtils.isEmpty(title)) {
//                    setShareTitle(title);
//                }
//                if (!TextUtils.isEmpty(content)) {
//                    setShareContent(content);
//                }
//            } else {
//                if (webViewActionListener == null) {
//                    return;
//                }
//                // 如果是立即显示，则直接分享
//                UMImage umImage = null;
//                if (!TextUtils.isEmpty(icon)) {
//                    umImage = new UMImage(getActivity(), icon);
//                }
//                webViewActionListener.openSharePanel(false, url, umImage, title, content);
//            }
//        });
//    }
//
//    // 页面跳转
//    public void androidViewSkipOnUiThread(final int type) {
//        getActivity().runOnUiThread(() -> {
//            switch (type) {
//                case ZpsConstants.VD_SHOPCARTACTIVITY://跳转购物车
//                    startActivity(new Intent(getActivity(), ShopCartActivity.class));
//                    break;
//                case ZpsConstants.V_BINDMOBILEACTIVITY://绑定手机
//                    startActivity(new Intent(getActivity(), BindMobileStep1Activity.class));
//                    break;
//                case ZpsConstants.V_ES_FEEDBACKACTIVITY://提交反馈
//                    startActivity(new Intent(getActivity(), FeedbackActivity.class));
//                    break;
//                case ZpsConstants.V_LOGINACTIVITY:
//                    toLogin(false);
//                    break;
//                case 31://个人信息
//                    startActivity(new Intent(getContext(), SelfInfoActivity.class));
//                    break;
//                case 41://待评价商品列表
//                    Intent intent = new Intent(getContext(), OrderListActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("type", OrderListActivity.TYPE_TO_EVALUATE);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                    break;
//                default:
//                    break;
//            }
//        });
//    }
//
//    // 添加到收藏
//    public void androidAddToCollectOnUiThread(final String productId) {
//        getActivity().runOnUiThread(() -> {
//            if (webViewApiRequestListener != null) {
//                webViewApiRequestListener.addToCollector(productId);
//            }
//        });
//    }
//
//    // 复制到剪切板
//    public void androidCopyOnUiThread(final String text) {
//        getActivity().runOnUiThread(() -> copyOrderId(getActivity(), null, text));
//    }
//
//    // 添加到购物车
//    public void androidAddToShopBagOnUiThread(final String specId) {
//        getActivity().runOnUiThread(() -> {
//            try {
//                fromTxt = URLEncoder.encode(mWebView.getUrl(), "utf-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            if (webViewApiRequestListener != null) {
//                webViewApiRequestListener.addToShopBag(specId);
//            }
//        });
//    }
//
//    // 跳转到商品列表页
//    public void androidToProductListOnUiThread(final int type, final String id, final String title, final String gender, final String filterBrand) {
//        getActivity().runOnUiThread(() -> {
//            if (type != 0 && !TextUtils.isEmpty(id)) {
//                switch (type) {
//                    case 1:
//                        //分类
//                        ProductListRoute.with(getContext())
//                                .fromId(webLaunchParams.getFromId())
//                                .fromType(SearchProductParams.FROM_CATEGORY)
//                                .title(title)
//                                .cId(id)
//                                .gender(gender)
//                                .filterBrand(filterBrand)
//                                .launch();
//                        break;
//                    case 2:
//                        //品牌
//                        ProductListRoute.with(getContext())
//                                .fromId(webLaunchParams.getFromId())
//                                .fromType(SearchProductParams.FROM_BRAND)
//                                .title(title)
//                                .brandId(id)
//                                .launch();
//                        break;
//                    case 3:
//                        //搜索
//                        ProductListRoute.with(getContext())
//                                .fromId(webLaunchParams.getFromId())
//                                .fromType(SearchProductParams.FROM_SEARCH)
//                                .title(title)
//                                .searchText(id)
//                                .launch();
//                        SharePreUtil.setHistoryWord(getActivity(), id, HISTORY_WORDS);
//                        break;
//                    default:
//                        showSnackbar(getActivity(), "参数异常");
//                        break;
//                }
//            } else {
//                showSnackbar(getActivity(), "参数异常");
//            }
//        });
//    }
//
//    // 跳转到商品列表页
//    public void androidToProductListOnUiThread(final String jsonStr) {
//        final ProductListWebParams productListWebParams = GsonUtil.jsonToObject(jsonStr, ProductListWebParams.class);
//        if (productListWebParams == null) {
//            LogUtil.w(WebContentView.class.getSimpleName(), "productListWebParams is null! jsonStr is " + jsonStr);
//            return;
//        }
//        getActivity().runOnUiThread(() -> {
//            int type = productListWebParams.getType();
//            String id = productListWebParams.getId();
//            String title = productListWebParams.getTitle();
//            String gender = productListWebParams.getGender();
//            String filterBrand = productListWebParams.getFilterBrand();
//            String specialId = productListWebParams.getSpecialId();
//            switch (type) {
//                case 1:
//                    //分类
//                    ProductListRoute.with(getContext())
//                            .fromId(webLaunchParams.getFromId())
//                            .fromType(SearchProductParams.FROM_CATEGORY)
//                            .title(title)
//                            .cId(id)
//                            .gender(gender)
//                            .filterBrand(filterBrand)
//                            .launch();
//                    break;
//                case 2:
//                    //品牌
//                    ProductListRoute.with(getContext())
//                            .fromId(webLaunchParams.getFromId())
//                            .fromType(SearchProductParams.FROM_BRAND)
//                            .title(title)
//                            .brandId(id)
//                            .launch();
//                    break;
//                case 3:
//                    //搜索
//                    ProductListRoute.with(getContext())
//                            .fromId(webLaunchParams.getFromId())
//                            .fromType(SearchProductParams.FROM_SEARCH)
//                            .title(title)
//                            .searchText(id)
//                            .launch();
//                    SharePreUtil.setHistoryWord(getActivity(), id, HISTORY_WORDS);
//                    break;
//                case 4:
//                    // 专题
//                    ProductListRoute.with(getContext())
//                            .fromId(webLaunchParams.getFromId())
//                            .fromType(SearchProductParams.FROM_CATEGORY)
//                            .title(title)
//                            .specialId(specialId)
//                            .launch();
//                default:
//                    LogUtil.w(WebContentView.class.getSimpleName(), "不支持的type类型:" + type);
//                    break;
//            }
//        });
//    }
//
//    /**
//     * 调用第三方浏览器打开
//     *
//     * @param context 上下文环境
//     * @param url     要浏览的资源地址
//     */
//    public static void openBrowser(Context context, String url) {
//        final Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(url));
//        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
//        // 官方解释 : Name of the component implementing an activity that can display the intent
//        if (intent.resolveActivity(context.getPackageManager()) != null) {
//            final ComponentName componentName = intent.resolveActivity(context.getPackageManager());
//            // 打印Log   ComponentName到底是什么
//            LogUtil.d("componentName = " + componentName.getClassName());
//            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
//        } else {
//            Toast.makeText(context.getApplicationContext(), "请下载浏览器", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private static final class ProductListWebParams {
//        private int type;
//        private String id;
//        private String title;
//        private String gender;
//        private String filterBrand;
//        private String specialId;
//
//        public int getType() {
//            return type;
//        }
//
//        public void setType(int type) {
//            this.type = type;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getGender() {
//            return gender;
//        }
//
//        public void setGender(String gender) {
//            this.gender = gender;
//        }
//
//        public String getFilterBrand() {
//            return filterBrand;
//        }
//
//        public void setFilterBrand(String filterBrand) {
//            this.filterBrand = filterBrand;
//        }
//
//        public String getSpecialId() {
//            return specialId;
//        }
//
//        public void setSpecialId(String specialId) {
//            this.specialId = specialId;
//        }
//    }
//
//    // 跳转到新的h5
//    public void androidToNewWebViewOnUiThread(final String title, final String htmlUrl, final String shareUrl) {
//        getActivity().runOnUiThread(() ->
//                WebLaunchRoute.with(getActivity())
//                        .title(title)
//                        .visitUrl(htmlUrl)
//                        .shareUrl(shareUrl)
//                        .launch());
//    }
//
//    // 跳转到订单列表
//    public void androidToOrderListOnUiThread(final int type) {
//        getActivity().runOnUiThread(() -> {
//            if (Session.get().isLogin()) {
//                Intent intent = new Intent(getActivity(), OrderListActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("type", type);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            } else {
//                toLogin(false);
//            }
//        });
//    }
//
//    private void androidToMainTabOnUiThread(final int index) {
//        getActivity().runOnUiThread(() -> goToHome(getContext(), index));
//    }
//
//    private void androidOnZhenApplyResultOnUiThread(final int result) {
//        getActivity().runOnUiThread(() -> {
//            switch (result) {
//                case 1:
//                case 2:
//                    getActivity().setResult(Activity.RESULT_OK);
//                    break;
//                case 3:
//                    getActivity().finish();
//                    break;
//                case 4:
//                    goToHome(getContext(), 0);
//                    break;
//                default:
//                    break;
//            }
//        });
//    }
//
//    public WebContentView with(Activity activity) {
//        this.mActivity = activity;
//        return this;
//    }
//
//    public WebContentView webLaunchParams(WebLaunchParams webLaunchParams) {
//        this.webLaunchParams = webLaunchParams;
//        if (webLaunchParams != null) {
//            this.lastUrl = webLaunchParams.getVisitUrl();
//        }
//        return this;
//    }
//
//    public WebContentView webViewActionListener(WebViewActionListener webViewActionListener) {
//        this.webViewActionListener = webViewActionListener;
//        return this;
//    }
//
//    public WebContentView webViewApiRequestListener(WebViewApiRequestListener webViewApiRequestListener) {
//        this.webViewApiRequestListener = webViewApiRequestListener;
//        return this;
//    }
//
//    public WebView getWebView() {
//        return mWebView;
//    }
//
//    public StatusView getStatusView() {
//        return mStatusView;
//    }
//
//    /**
//     * handler处理消息机制
//     */
//    @SuppressLint("HandlerLeak")
//    protected Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message message) {
//            switch (message.what) {
//                case LOAD_SUCCESS_WEBVIEW:
//                    dismissProgressView();
//                    if (mWebView != null) {
//                        mWebView.setVisibility(View.VISIBLE);
//                        if (webViewActionListener != null) {
//                            webViewActionListener.onPageFinished();
//                        }
//                    }
//                    break;
//                case LOAD_ERROR_WEBVIEW:
//                    onLoadError();
//                    break;
//                case LOAD_UPDATE_SHARE_INFO:
//                    if (mWebView != null) {
//                        String jsCodeString = " try {\n" +
//                                "                JShare()\n" +
//                                "            } catch (e) {\n" +
//                                "            }";
//                        mWebView.loadUrl("javascript:" + jsCodeString);
//                        LogUtil.i(WebContentView.class.getSimpleName(), "LOAD_UPDATE_SHARE_INFO");
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
//
//    public interface WebViewActionListener {
//        void startActivity(Intent intent);
//
//        void startActivityForResult(Intent intent, int requestCode);
//
//        void updateTitle(WebView webView, String cTitle);
//
//        void onPageFinished();
//
//        void buyAtOnce(String url, int fromid, String fromtxt);
//
//        /**
//         * 打开分享面板
//         *
//         * @param isShowAllPlatform 是否显示所有分享平台
//         * @param url               分享URL
//         * @param image             分享图片
//         * @param title             分享标题
//         * @param content           分享内容
//         */
//        void openSharePanel(boolean isShowAllPlatform, String url, UMImage image, String title, String content);
//
//        /**
//         * 是否显示客服图标
//         *
//         * @param isShow 是否显示 true:显示 false：不显示
//         * @param json   商品信息JSON字符串
//         */
//        void isTitleBarShowService(boolean isShow, String json);
//    }
//
//    public interface WebViewApiRequestListener {
//
//        /**
//         * 添加收藏
//         *
//         * @param productId 商品ID
//         */
//        void addToCollector(String productId);
//
//        /**
//         * 添加购物车
//         *
//         * @param specId
//         */
//        void addToShopBag(String specId);
//    }
//
//    private class MyWebViewDownLoadListener implements DownloadListener {
//
//        @Override
//        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            startActivity(intent);
//        }
//    }
//}