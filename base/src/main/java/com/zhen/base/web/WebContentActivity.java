//package com.zhen.base.web;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.webkit.WebView;
//
//import androidx.databinding.DataBindingUtil;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.zhenpin.luxurystore.R;
//import com.zhenpin.luxurystore.databinding.ActivityWebContentBinding;
//import com.zhenpin.luxurystore.main.base.SuperActivity;
//import com.zhenpin.luxurystore.model.WebViewHeaders;
//import com.zhenpin.luxurystore.model.product.ProductDetail;
//import com.zhenpin.luxurystore.utils.GsonUtil;
//import com.zhenpin.luxurystore.utils.SystemBarTintManager;
//import com.zhenpin.luxurystore.utils.ViewUtil;
//import com.zhenpin.luxurystore.utils.XiaonengChatUtil;
//
///**
// * H5界面
// * 与{@link WebContentFragment})配合使用
// */
//public class WebContentActivity extends SuperActivity implements View.OnClickListener {
//    public static final int COMMON_WEB_TOLOGIN_RESULT = 0x028;
//    private WebContentFragment fragment;
//    private boolean isTransparentFrom;
//    private String helpUrl;
//    private ProductDetail productDetail;
//
//    public static void launchForResult(Context context, String title, String htmlUrl, String shareUrl, boolean isTitleBarGone,
//                                       boolean isTransparentFrom, int fromId, int requestCode, int flags, String helpUrl, WebViewHeaders webViewHeaders) {
//        Intent intent = getWebIntent(context, title, htmlUrl, shareUrl, isTitleBarGone, isTransparentFrom, fromId, flags, helpUrl, webViewHeaders);
//        if (context instanceof Activity) {
//            ((Activity) context).startActivityForResult(intent, requestCode);
//        } else {
//            context.startActivity(intent);
//        }
//    }
//
//    public static Intent getWebIntent(Context context, String title, String htmlUrl, String shareUrl, boolean isTitleBarGone,
//                                      boolean isTransparentFrom, int fromId, int flags, String helpUrl, WebViewHeaders webViewHeaders) {
//        Intent intent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putString("htmlurl", htmlUrl);
//        bundle.putString("title", title);
//        bundle.putString("realurl", shareUrl);
//        bundle.putBoolean("isTitleBarGone", isTitleBarGone);
//        bundle.putBoolean("isTransparentFrom", isTransparentFrom);
//        bundle.putInt("fromid", fromId);
//        bundle.putString("helpUrl", helpUrl);
//        bundle.putSerializable("webViewHeaders", webViewHeaders);
//        if (flags != -1) {
//            intent.addFlags(flags);
//        }
//        intent.putExtras(bundle);
//        intent.setClass(context, WebContentActivity.class);
//        return intent;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        activityWebContentBinding = DataBindingUtil.setContentView(this, R.layout.activity_web_content);
//        //H5界面主题设置为透明主题了，所以需要通过设置背景颜色实现非透明主题需求
//        if (isTransparentFrom) {
//            activityWebContentBinding.llWebContent.setBackgroundColor(Color.TRANSPARENT);
//        } else {
//            activityWebContentBinding.llWebContent.setBackgroundColor(getResources().getColor(R.color.gray_e5e5e5));
//        }
//        initFragment(savedInstanceState);
//    }
//
//    @Override
//    protected void immerseStatusBar() {
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            isTransparentFrom = extras.getBoolean("isTransparentFrom");
//        }
//        if (isTransparentFrom) {
//            SystemBarTintManager.setStatusBarTintMode(getWindow(), false, Color.TRANSPARENT);
//        } else {
//            super.immerseStatusBar();
//        }
//    }
//
//    @Override
//    protected void initViews() {
//    }
//
//    @Override
//    protected void initEvents() {
//    }
//
//    private void initFragment(Bundle savedInstanceState) {
//        Bundle extras = getIntent().getExtras();
//        WebLaunchParams webLaunchParams = null;
//        if (null != extras) {
//            String visitUrl = extras.getString("htmlurl");
//            if (TextUtils.isEmpty(visitUrl)) {
//                finish();
//                return;
//            }
//            String title = extras.getString("title");
//            if (!TextUtils.isEmpty(title)) {
//                title = title.replaceAll("##", "");
//            }
//            String shareUrl = extras.getString("realurl");
//            helpUrl = extras.getString("helpUrl");
//            boolean isTitleBarGone = extras.getBoolean("isTitleBarGone");
//            int fromId = extras.getInt("fromid", 4);
//            boolean isShare = !TextUtils.isEmpty(shareUrl);
//            boolean isHelp = !TextUtils.isEmpty(helpUrl);
//            WebViewHeaders webViewHeaders = (WebViewHeaders) extras.getSerializable("webViewHeaders");
//            initTitleView(title, isShare, isTitleBarGone, isHelp);
//            webLaunchParams = new WebLaunchParams();
//            webLaunchParams.setTitleTxt(title);
//            webLaunchParams.setVisitUrl(visitUrl);
//            webLaunchParams.setShareUrl(shareUrl);
//            webLaunchParams.setHelpUrl(helpUrl);
//            webLaunchParams.setShare(isShare);
//            webLaunchParams.setHelp(isHelp);
//            webLaunchParams.setTitleBarGone(isTitleBarGone);
//            webLaunchParams.setTransparentFrom(isTransparentFrom);
//            webLaunchParams.setFromId(fromId);
//            webLaunchParams.setActivity(true);
//            webLaunchParams.setWebViewHeaders(webViewHeaders);
//        }
//        FragmentManager fm = getSupportFragmentManager();
//        if (savedInstanceState != null) {
//            fragment = (WebContentFragment) fm.findFragmentByTag(WebContentFragment.class.getSimpleName());
//        }
//        if (fragment == null) {
//            fragment = WebContentFragment.newInstance(webLaunchParams);
//        }
//        if (!fragment.isAdded()) {
//            FragmentTransaction transaction = fm.beginTransaction();
//            transaction.add(R.id.fra_container, fragment, WebContentFragment.class.getSimpleName());
//            transaction.commit();
//        }
//        fragment.setWebContentCallback(new WebContentFragment.WebContentCallback() {
//            @Override
//            public void updateTitle(WebView webView, String cTitle) {
//                runOnUiThread(() -> getTitleBar().title(cTitle));
//            }
//
//            @Override
//            public void updateShareVisibility(boolean isVisible) {
//                WebContentActivity.this.updateShareVisibility(isVisible);
//            }
//
//            @Override
//            public void updateHelpVisibility(boolean isVisible) {
//                WebContentActivity.this.updateHelpVisibility(isVisible);
//            }
//
//            @Override
//            public void isTitleBarShowService(boolean isShow, String json) {
//                runOnUiThread(() -> WebContentActivity.this.isTitleBarShowService(isShow));
//                if (!TextUtils.isEmpty(json)) {
//                    productDetail = GsonUtil.jsonToObject(json, ProductDetail.class);
//                }
//            }
//        });
//    }
//
//    private void initTitleView(String title, boolean isShare, boolean isTitleBarGone, boolean isHelp) {
//        if (isTransparentFrom) {
//            activityWebContentBinding.imgClosePage.setVisibility(View.VISIBLE);
//            activityWebContentBinding.imgClosePage.setOnClickListener(v -> finish());
//        }
//        if (isShare) {
//            //可以分享
//            getTitleBar()
//                    .title(title)
//                    .addRightImageItem(R.id.img_right_service, R.mipmap.icon_server_sign, this)
//                    .addRightImageItem(R.id.img_right_more, R.mipmap.icon_title_more, this);
//        } else if (isHelp) {
//            getTitleBar()
//                    .title(title)
//                    .addRightImageItem(R.id.img_right_service, R.mipmap.icon_server_sign, this)
//                    .addRightImageItem(R.id.img_right_help, R.drawable.icon_help_black2, this);
//        } else {
//            getTitleBar()
//                    .title(title)
//                    .addRightImageItem(R.id.img_right_service, R.mipmap.icon_server_sign, this);
//        }
//        isTitleBarShowService(false);
//        if (isTitleBarGone) {
//            getTitleBar().setVisibility(View.GONE);
//        }
//    }
//
//    public void updateShareVisibility(boolean isVisible) {
//        if (findViewById(R.id.img_right_more) == null) {
//            return;
//        }
//        findViewById(R.id.img_right_more).setVisibility(isVisible ? View.VISIBLE : View.GONE);
//    }
//
//    public void updateHelpVisibility(boolean isVisible) {
//        if (findViewById(R.id.img_right_help) == null) {
//            return;
//        }
//        findViewById(R.id.img_right_help).setVisibility(isVisible ? View.VISIBLE : View.GONE);
//    }
//
//    public void isTitleBarShowService(boolean isShow) {
//        if (findViewById(R.id.img_right_service) == null) {
//            return;
//        }
//        findViewById(R.id.img_right_service).setVisibility(isShow ? View.VISIBLE : View.GONE);
//    }
//
//    public String getCurrentUrl() {
//        if (fragment == null) {
//            return null;
//        }
//        return fragment.getCurrentUrl();
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (fragment != null) {
//            fragment.onBackPressed();
//        }
//    }
//
//    @Override
//    public void finish() {
//        super.finish();
//        if (isTransparentFrom) {
//            overridePendingTransition(0, R.anim.alpha_out);
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.img_right_service://客服
//                if (productDetail == null) {
//                    XiaonengChatUtil.getInstance().startCommonChat(this);
//                } else {
//                    XiaonengChatUtil.getInstance().startProductDetialChat2(this, productDetail);
//                }
//                ViewUtil.pushSensorsDataMethod(this, "webpage", "customer_service");
//                break;
//            case R.id.img_right_more://更多
//                if (fragment == null || findViewById(R.id.img_right_more) == null) {
//                    return;
//                }
//                fragment.showOptionMenu(findViewById(R.id.img_right_more));
//                break;
//            case R.id.img_right_help://帮助
//                if (TextUtils.isEmpty(helpUrl)) {
//                    return;
//                }
//                WebLaunchRoute.with(WebContentActivity.this)
//                        .visitUrl(helpUrl)
//                        .launch();
//                break;
//            default:
//                break;
//        }
//    }
//}