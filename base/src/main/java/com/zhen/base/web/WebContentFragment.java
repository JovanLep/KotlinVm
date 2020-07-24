//package com.zhen.base.web;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebView;
//import android.widget.AdapterView;
//import android.widget.ListPopupWindow;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
//import com.umeng.socialize.UMShareListener;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.media.UMImage;
//import com.zhenpin.luxurystore.Constants;
//import com.zhenpin.luxurystore.R;
//import com.zhenpin.luxurystore.assistor.ZpsAssistor;
//import com.zhenpin.luxurystore.assistor.ZpsConstants;
//import com.zhenpin.luxurystore.beans.BooleanResultBean;
//import com.zhenpin.luxurystore.beans.ButtonInfo;
//import com.zhenpin.luxurystore.beans.CartNumBean;
//import com.zhenpin.luxurystore.main.base.SuperActivity;
//import com.zhenpin.luxurystore.main.order.adapter.ButtonInfoListAdapter;
//import com.zhenpin.luxurystore.main.search.activity.SearchActivity;
//import com.zhenpin.luxurystore.main.shopcart.activity.ShopCartActivity;
//import com.zhenpin.luxurystore.net.ZpApiRequestListener;
//import com.zhenpin.luxurystore.net.ZpHttpRequest;
//import com.zhenpin.luxurystore.utils.ScreenUtil;
//import com.zhenpin.luxurystore.utils.SnackbarUtil;
//import com.zhenpin.luxurystore.utils.ViewUtil;
//import com.zhenpin.luxurystore.utils.XiaonengChatUtil;
//import com.zhenpin.luxurystore.view.UMShareAgent;
//import com.zhenpin.luxurystore.view.ptr.StatusView;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
//import io.reactivex.disposables.CompositeDisposable;
//import io.reactivex.disposables.Disposable;
//
//import static androidx.localbroadcastmanager.content.LocalBroadcastManager.*;
//import static com.zhenpin.luxurystore.utils.RefreshUtil.refreshShoppingCart;
//import static com.zhenpin.luxurystore.utils.SkipUtil.goToHome;
//import static com.zhenpin.luxurystore.utils.SnackbarUtil.showSnackbar;
//import static com.zhenpin.luxurystore.utils.ZhenInterfaceUtil.updateCartNum;
//
///**
// * 公共的WebView显示fragment
// * 参见{@link WebContentActivity})
// */
//public class WebContentFragment extends Fragment implements WebContentView.WebViewActionListener, WebContentView.WebViewApiRequestListener {
//    private WebContentCallback webContentCallback;
//    private WebLaunchParams webLaunchParams;
//    private WebContentView webContentView;
//    private WebView mWebView;
//    private UMShareAgent umShareAgent;
//    private boolean isNeedRefresh = false;
//    private CompositeDisposable compositeDisposable;
//
//    public interface WebContentCallback {
//        void updateTitle(WebView webView, String cTitle);
//
//        void updateShareVisibility(boolean isVisible);
//
//        void updateHelpVisibility(boolean isVisible);
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
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_web_content, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        if (savedInstanceState != null) {
//            webLaunchParams = savedInstanceState.getParcelable("webLaunchParams");
//        } else {
//            Bundle bundle = getArguments();
//            if (null != bundle) {
//                webLaunchParams = bundle.getParcelable("webLaunchParams");
//            }
//        }
//        if (webContentView != null) {
//            webContentView.destroy();
//        }
//        initViews(view);
//        // 监听登录状态变化
//        IntentFilter mLoginChangedFilter = new IntentFilter();
//        mLoginChangedFilter.addAction(Constants.ACTION_LOGIN_CHANGED);
//        getInstance(getActivity()).registerReceiver(mLoginChangedReceiver, mLoginChangedFilter);
//        loadData();
//        SensorsDataAPI.sharedInstance().showUpWebView(mWebView, false, null);
//    }
//
//    public static WebContentFragment newInstance(WebLaunchParams webLaunchParams) {
//        WebContentFragment fg = new WebContentFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("webLaunchParams", webLaunchParams);
//        fg.setArguments(bundle);
//        return fg;
//    }
//
//    private void initViews(View view) {
//        webContentView = view.findViewById(R.id.webContentView);
//        webContentView.with(getActivity())
//                .webLaunchParams(webLaunchParams)
//                .webViewActionListener(this)
//                .webViewApiRequestListener(this);
//        mWebView = webContentView.getWebView();
//        if (webLaunchParams != null && webLaunchParams.isTransparentFrom()) {
//            mWebView.setBackgroundColor(Color.TRANSPARENT);
//        }
//    }
//
//    /**
//     * 加载url
//     */
//    public void loadData() {
//        if (webContentView != null) {
//            webContentView.loadData();
//        }
//    }
//
//    public void updateWebLunchParams(String title, String visitUrl, String share) {
//        if (webLaunchParams != null) {
//            webLaunchParams.setTitleTxt(title);
//            webLaunchParams.setVisitUrl(visitUrl);
//            webLaunchParams.setShare(true);
//            webLaunchParams.setShareUrl(share);
//        }
//    }
//
//    public void putCookieAndLoadUrl(String visitUrl) {
//        if (webContentView != null) {
//            webContentView.loadUrlAndResetCookieIfNeed(visitUrl);
//        }
//    }
//
//    public String getCurrentUrl() {
//        if (mWebView == null) {
//            return null;
//        }
//        return mWebView.getUrl();
//    }
//
//    @Override
//    public void updateTitle(WebView webView, String cTitle) {
//        if (webContentCallback != null) {
//            webContentCallback.updateTitle(webView, cTitle);
//        }
//    }
//
//    @Override
//    public void onPageFinished() {
//        if (webContentCallback != null) {
//            webContentCallback.updateShareVisibility(webLaunchParams.isShare() && !TextUtils.isEmpty(webContentView.getShareContent()));
//            webContentCallback.updateHelpVisibility(webLaunchParams.isHelp());
//        }
//    }
//
//    @Override
//    public void buyAtOnce(String url, int fromid, String fromtxt) {
//        // 立即购买(直接下单)
//        if (getActivity() != null && getActivity() instanceof SuperActivity) {
//            ((SuperActivity) getActivity()).buyAtOnce(url, fromid, fromtxt);
//        }
//    }
//
//    public void openSharePanel() {
//        UMImage shareImage = null;
//        if (null != webContentView.getShareIcon()) {
//            shareImage = new UMImage(getActivity(), webContentView.getShareIcon());
//        }
//        openSharePanel(true, webContentView.getShareUrl(), shareImage, webContentView.getShareTitle(), webContentView.getShareContent());
//    }
//
//    @Override
//    public void openSharePanel(boolean isShowAllPlatform, String url, UMImage image, String title, String content) {
//        // 打开分享面板
//        if (umShareAgent == null) {
//            umShareAgent = new UMShareAgent(getActivity());
//        }
//        umShareAgent.oneKeyShare(isShowAllPlatform, title, content, image, url);
//        umShareAgent.setOnShareResultListener(new UMShareListener() {
//            @Override
//            public void onStart(SHARE_MEDIA share_media) {
//
//            }
//
//            @Override
//            public void onResult(SHARE_MEDIA share_media) {
//                mWebView.loadUrl("javascript:shareResult(true)");
//            }
//
//            @Override
//            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//                mWebView.loadUrl("javascript:shareResult(false)");
//            }
//
//            @Override
//            public void onCancel(SHARE_MEDIA share_media) {
//
//            }
//        });
//        umShareAgent.show();
//    }
//
//    @Override
//    public void isTitleBarShowService(boolean isShow, String json) {
//        webContentCallback.isTitleBarShowService(isShow, json);
//    }
//
//    public void setWebContentCallback(WebContentCallback webContentCallback) {
//        this.webContentCallback = webContentCallback;
//    }
//
//    public StatusView getStatusView() {
//        if (webContentView == null) {
//            return null;
//        }
//        return webContentView.getStatusView();
//    }
//
//    public boolean isNeedRefresh() {
//        return this.isNeedRefresh;
//    }
//
//    public void setNeedRefresh(boolean needRefresh) {
//        this.isNeedRefresh = needRefresh;
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (webContentView != null) {
//            webContentView.onActivityResult(requestCode, resultCode, data);
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (webContentView != null) {
//            webContentView.onPause();
//        }
//    }
//
//    @Override
//    public void onResume() {
//        if (webContentView != null) {
//            webContentView.onResume();
//            if (isNeedRefresh) {
//                isNeedRefresh = false;
//                loadData();
//            }
//        }
//        super.onResume();
//    }
//
//    public void onBackPressed() {
//        if (webContentView != null) {
//            webContentView.onBackPressed();
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        dispose();
//        super.onDestroyView();
//        if (webContentView != null) {
//            webContentView.onDestroyView();
//        }
//        try {
//            if (null != mLoginChangedReceiver) {
//                getInstance(getActivity()).unregisterReceiver(mLoginChangedReceiver);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        if (webContentView != null) {
//            webContentView.onDestroy();
//        }
//        super.onDestroy();
//    }
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if (webLaunchParams != null) {
//            outState.putParcelable("webLaunchParams", webLaunchParams);
//        }
//    }
//
//    //监听登录状态变化的广播接收器
//    private BroadcastReceiver mLoginChangedReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            isNeedRefresh = true;
//        }
//    };
//
//    // *****************************************************************************************************
//
//    @Override
//    public void addToCollector(final String productId) {
//        addDisposable(ZpHttpRequest.getInstance().addToCollection(new ZpApiRequestListener<BooleanResultBean>() {
//            @Override
//            public void onSuccess(BooleanResultBean obj) {
//                dismissProgressView();
//                onAddToCollectorSuccess(obj, productId);
//            }
//
//            @Override
//            public void onError(int errorCode, String errorMsg) {
//                dismissProgressView();
//                showSnackbar(WebContentFragment.this, errorMsg);
//            }
//        }, productId));
//    }
//
//    @Override
//    public void addToShopBag(final String specId) {
//        addDisposable(ZpHttpRequest.getInstance().addToShopBag(new ZpApiRequestListener<CartNumBean>() {
//            @Override
//            public void onSuccess(CartNumBean obj) {
//                dismissProgressView();
//                onAddToShopBagSuccess(specId);
//            }
//
//            @Override
//            public void onError(int errorCode, String errorMsg) {
//                dismissProgressView();
//                showSnackbar(WebContentFragment.this, errorMsg);
//            }
//        }, specId, "1"));
//    }
//
//    public void onAddToCollectorSuccess(BooleanResultBean result, String productId) {
//        if (result.isOk()) {
//            HashMap<String, String> map = new HashMap<>(16);
//            map.put("productid", productId);
//            map.put("isAddCollection", "Y");
//            ZpsAssistor.onEvent(getActivity(), ZpsConstants.AT_COLLECT, ZpsConstants.V_PRODUCTDETAIL, map);
//            SnackbarUtil.showSnackbar(getActivity(),R.string.collect_add_success);
//            try {
//                JSONObject properties = new JSONObject();
//                properties.put("commodityID", productId);
//                properties.put("collectEntrance", "web");
//                SensorsDataAPI.sharedInstance(getActivity()).track("collect", properties);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
//            showSnackbar(WebContentFragment.this, R.string.collect_add_failed);
//        }
//    }
//
//    public void onAddToShopBagSuccess(String specId) {
//        String fromTxt = "";
//        try {
//            fromTxt = URLEncoder.encode(mWebView.getUrl(), "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        SnackbarUtil.showSnackbar(getActivity(),"加入购物袋成功");
//        HashMap<String, String> map = new HashMap<>(16);
//        map.put("productspecid", specId);
//        map.put("fromId", webLaunchParams.getFromId() + "");
//        map.put("fromTxt", fromTxt);
//        ZpsAssistor.onEvent(getActivity(), ZpsConstants.AT_PRODUCTADDBAG, ZpsConstants.V_COMMONWEBCONTENT, map);
//        addDisposable(updateCartNum());
//        refreshShoppingCart(true);
//        try {
//            JSONObject properties = new JSONObject();
//            properties.put("commodityID", specId);
//            properties.put("shoppingcartEntrance", "web");
//            SensorsDataAPI.sharedInstance(getActivity()).track("addToShoppingcart", properties);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void dismissProgressView() {
//        if (webContentView != null && webContentView.getStatusView() != null) {
//            webContentView.getStatusView().dismissProgressView();
//        }
//    }
//
//    /**
//     * 显示右上角菜单
//     *
//     * @param anchor 菜单跟随视图
//     */
//    public void showOptionMenu(View anchor) {
//        if (getContext() == null) {
//            return;
//        }
//        getContext().setTheme(R.style.list_popup_window_style2);
//        final ListPopupWindow listPopupWindow = new ListPopupWindow(getContext());
//        listPopupWindow.setWidth(ScreenUtil.dip2px(getContext(), 116f));
//        listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
//        listPopupWindow.setAnchorView(anchor);
//        //相对锚点偏移值，正值表示向右偏移
//        listPopupWindow.setHorizontalOffset(0 - ScreenUtil.dip2px(getContext(), 82f));
//        //setVerticalOffset方法传正值代表向下移动，正值代表向上移动
//        listPopupWindow.setVerticalOffset(0 - ScreenUtil.dip2px(getContext(), 5f));
//        listPopupWindow.setBackgroundDrawable(this.getResources().getDrawable(android.R.color.transparent));
//        final List<ButtonInfo> buttonInfoList = new ArrayList<>();
//        List<String> optionMenuTextList = Arrays.asList(getContext().getResources().getStringArray(R.array.optionMenuText));
//        for (int i = 0; i < optionMenuTextList.size(); i++) {
//            ButtonInfo buttonInfo = new ButtonInfo();
//            buttonInfo.setType(Constants.OPTION_MENU_TYPE_ARRAY[i]);
//            buttonInfo.setIconResourceId(Constants.OPTION_MENU_ICON_ARRAY[i]);
//            buttonInfo.setText(optionMenuTextList.get(i));
//            buttonInfoList.add(buttonInfo);
//        }
//        ButtonInfoListAdapter buttonInfoListAdapter = new ButtonInfoListAdapter(getContext(), buttonInfoList, true, ScreenUtil.dip2px(getContext(), 4f));
//        buttonInfoListAdapter.setGravity(Gravity.CENTER_VERTICAL);
//        buttonInfoListAdapter.setTextSize(14f);
//        buttonInfoListAdapter.setHeight(ScreenUtil.dip2px(getContext(), 40f));
//        buttonInfoListAdapter.setPaddingHorizontal(ScreenUtil.dip2px(getContext(), 15f));
//        buttonInfoListAdapter.setBackgroundColor(getContext().getResources().getColor(R.color.gray_1f1f1f_8));
//        listPopupWindow.setAdapter(buttonInfoListAdapter);
//        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                listPopupWindow.dismiss();
//                ButtonInfo buttonInfo = buttonInfoList.get(position);
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                switch (buttonInfo.getType()) {
//                    case Constants.INTEGER_GO_TO_HOME://首页
//                        goToHome(getContext(), 0);
//                        ViewUtil.pushSensorsDataMethod(getContext(), "webpage", "more_homepage");
//                        break;
//                    case Constants.INTEGER_GO_TO_SEARCH://搜索
//                        intent.setClass(getContext(), SearchActivity.class);
//                        bundle.putString("from", getClass().getName());
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                        ViewUtil.pushSensorsDataMethod(getContext(), "webpage", "more_searchbox");
//                        break;
//                    case Constants.INTEGER_GO_TO_SHOPPING_CART://购物车
//                        startActivity(new Intent(getActivity(), ShopCartActivity.class));
//                        ViewUtil.pushSensorsDataMethod(getContext(), "webpage", "more_shoppingcart");
//                        break;
//                    case Constants.INTEGER_GO_TO_SERVER://客服
//                        XiaonengChatUtil.getInstance().startCommonChat(getActivity());
//                        ViewUtil.pushSensorsDataMethod(getContext(), "webpage", "more_customer_service");
//                        break;
//                    case Constants.INTEGER_GO_TO_SHARE://分享
//                        openSharePanel();
//                        ViewUtil.pushSensorsDataMethod(getContext(), "webpage", "more_share");
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//        listPopupWindow.show();
//    }
//
//    /**
//     * 添加订阅
//     *
//     * @param disposable 订阅
//     */
//    private void addDisposable(Disposable disposable) {
//        if (disposable == null) {
//            return;
//        }
//        if (compositeDisposable == null) {
//            compositeDisposable = new CompositeDisposable();
//        }
//        compositeDisposable.add(disposable);
//    }
//
//    /**
//     * 取消订阅
//     */
//    private void dispose() {
//        if (compositeDisposable != null) {
//            compositeDisposable.dispose();
//            compositeDisposable = null;
//        }
//    }
//}