//package com.zhen.base.web;
//
//import android.animation.Animator;
//import android.animation.ObjectAnimator;
//import android.animation.PropertyValuesHolder;
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Build;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//import android.view.Display;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//
//import com.umeng.socialize.ShareAction;
//import com.umeng.socialize.UMShareAPI;
//import com.umeng.socialize.UMShareListener;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.media.UMImage;
//import com.umeng.socialize.media.UMWeb;
//import com.zhenpin.luxurystore.R;
//import com.zhenpin.luxurystore.net.ZpLuxuryApi;
//
//import java.util.UUID;
//
//
///**
// * @author
// * @ClassName: UMShareAgent
// * @Description:
// * @date
// */
//public class UMShareAgent implements View.OnClickListener {
//    private static UMShareAPI mShareAPI = null;
//    private ShareAction mShareAction = null;
//    private Activity activity;
//    private ImageView imageWeChat, imageCircle,
//            imageQQ, imageSina, imageCopy;
//    private Dialog shareDialogWindow;
//    private String title = "";
//    private String content = "";
//    private UMImage shareImage = null;
//    private String shareUrl = "";
//    private String weiboContent;  // 微博分享内容
//    private String weixinContent; // 微信好友分享内容
//    private OnShareClickListener onShareClickListener;
//    private UMShareListener onShareResultListener;
//
//    public interface OnShareClickListener {
//        void onClick(String platform);
//    }
//
//    public UMShareAgent(Activity a) {
//        this.activity = a;
//        initUMService();
//    }
//
//    /**
//     * @throws
//     * @Title: initUMService
//     * @Description: 初始化
//     */
//    private void initUMService() {
//        if (mShareAPI == null) {
//            mShareAPI = UMShareAPI.get(activity);
//        }
//        mShareAction = new ShareAction(activity);
//        configPlatforms();
//    }
//
//    public void setOnShareClickListener(OnShareClickListener onShareClickListener) {
//        this.onShareClickListener = onShareClickListener;
//    }
//
//    public void setOnShareResultListener(UMShareListener onShareResultListener) {
//        this.onShareResultListener = onShareResultListener;
//    }
//
//    public void setWeiboContent(String weiboContent) {
//        this.weiboContent = weiboContent;
//    }
//
//    public void setWeixinContent(String weixinContent) {
//        this.weixinContent = weixinContent;
//    }
//
//    /**
//     * @param @param a 当前的上下文
//     * @param @param title 分享的标题
//     * @param @param url 分享的内容，实际显示的就是个URL
//     * @param @param imgUrl 分享的图片URL
//     * @param @param shareUrl 点击后跳转目标的URL
//     * @return void
//     * @throws
//     * @Title: oneKeyShare
//     * @Description: 一键分享传入的内容
//     */
//    public void oneKeyShare(Boolean isShowAll, String title, String content, UMImage image, String shareUrl) {
//        if (title != null) {
//            this.title = title;
//        }
//        if (content != null) {
//            this.content = content;
//        }
//        if (image != null) {
//            this.shareImage = image;
//        }
//        if (shareUrl != null) {
//            this.shareUrl = shareUrl;
//        }
//        initView(activity, isShowAll);
//    }
//
//    /**
//     * @return
//     * @功能描述 : 添加平台分享(新浪、QQ和QQ空间、微信和微信朋友圈)
//     */
//    private void configPlatforms() {
//        addOtherPlatform();
//        mShareAction.setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA);
//        mShareAction.setCallback(new UMShareListener() {
//            @Override
//            public void onStart(SHARE_MEDIA shareMedia) {
//
//            }
//
//            @Override
//            public void onResult(SHARE_MEDIA shareMedia) {
//                sendSharePlatform(shareMedia);
//                showSnackbar(activity, "分享成功");
//                if (onShareResultListener != null) {
//                    onShareResultListener.onResult(shareMedia);
//                }
//            }
//
//            @Override
//            public void onError(SHARE_MEDIA shareMedia, Throwable throwable) {
//                showSnackbar(activity, "分享失败");
//                if (onShareResultListener != null) {
//                    onShareResultListener.onError(shareMedia, throwable);
//                }
//            }
//
//            @Override
//            public void onCancel(SHARE_MEDIA shareMedia) {
//                showSnackbar(activity, "分享取消");
//                if (onShareResultListener != null) {
//                    onShareResultListener.onCancel(shareMedia);
//                }
//            }
//        });
//    }
//
//    /**
//     * @return
//     * @功能描述 : 添加自定义平台分享
//     */
//    public void addOtherPlatform() {
//        mShareAction.addButton("复制链接", "copy_link", "link", "link");
//    }
//
//    /**
//     * @param @param context
//     * @return void
//     * @throws
//     * @Title: initView
//     * @Description: 初始化友盟分享自定义面板上的控件，设置点击事件
//     */
//    private void initView(Context context, Boolean isShowBtn) {
//        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_share_board, null);
//        RelativeLayout rlShare = rootView.findViewById(R.id.rl_share);
//        rlShare.setOnClickListener(this);
//        LinearLayout linShareFoot = rootView.findViewById(R.id.lin_share_foot);
//        linShareFoot.setOnClickListener(this);
//        LinearLayout myImageButtonWeChat = rootView.findViewById(R.id.wechat);
//        imageWeChat = rootView.findViewById(R.id.img_wechat);
//        myImageButtonWeChat.setOnClickListener(this);
//        LinearLayout myImageButtonCircle = rootView.findViewById(R.id.wechat_circle);
//        imageCircle = rootView.findViewById(R.id.img_wechat_circle);
//        myImageButtonCircle.setOnClickListener(this);
//        LinearLayout myImageButtonQQ = rootView.findViewById(R.id.qq);
//        imageQQ = rootView.findViewById(R.id.img_qq);
//        myImageButtonQQ.setOnClickListener(this);
//        LinearLayout myImageButtonCopy = rootView.findViewById(R.id.copy);
//        imageCopy = rootView.findViewById(R.id.img_copy);
//        myImageButtonCopy.setOnClickListener(this);
//        LinearLayout myImageButtonSina = rootView.findViewById(R.id.sina);
//        imageSina = rootView.findViewById(R.id.img_sina);
//        myImageButtonSina.setOnClickListener(this);
//        if (isShowBtn) {
//            myImageButtonSina.setVisibility(View.VISIBLE);
//            myImageButtonCopy.setVisibility(View.VISIBLE);
//        } else {
//            myImageButtonSina.setVisibility(View.GONE);
//            myImageButtonCopy.setVisibility(View.GONE);
//        }
//        shareDialogWindow = new Dialog(activity);
//        shareDialogWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        shareDialogWindow.getWindow().setWindowAnimations(R.style.shareWindowStyle);
//        shareDialogWindow.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        shareDialogWindow.setCancelable(true);
//        shareDialogWindow.setContentView(rootView);
//    }
//
//    public void show() {
//        if (shareDialogWindow.isShowing()) {
//            return;
//        }
//        shareDialogWindow.show();
//        WindowManager windowManager = activity.getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        WindowManager.LayoutParams lp = shareDialogWindow.getWindow().getAttributes();
//        lp.width = display.getWidth(); //设置宽度
//        shareDialogWindow.getWindow().setAttributes(lp);
//    }
//
//    /**
//     * (非 Javadoc)
//     * Title: onClick
//     * Description:分享面板中对应条目的点击事件
//     *
//     * @param v
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        switch (id) {
//            case R.id.wechat:
//                if (!mShareAPI.isInstall(activity, SHARE_MEDIA.WEIXIN)) {
//                    return;
//                }
//                if (!TextUtils.isEmpty(weixinContent)) {
//                    content = weixinContent;
//                }
//                if (onShareClickListener != null) {
//                    onShareClickListener.onClick("微信");
//                }
//                setAnimationAndSharePlates(SHARE_MEDIA.WEIXIN, imageWeChat);
//                break;
//            case R.id.wechat_circle:
//                if (!mShareAPI.isInstall(activity, SHARE_MEDIA.WEIXIN)) {
//                    return;
//                }
//                if (onShareClickListener != null) {
//                    onShareClickListener.onClick("朋友圈");
//                }
//                setAnimationAndSharePlates(SHARE_MEDIA.WEIXIN_CIRCLE, imageCircle);
//                break;
//            case R.id.qq:
//                if (onShareClickListener != null) {
//                    onShareClickListener.onClick("QQ");
//                }
//                setAnimationAndSharePlates(SHARE_MEDIA.QQ, imageQQ);
//                break;
//            case R.id.sina:
//                if (!TextUtils.isEmpty(weiboContent)) {
//                    content = weiboContent;
//                }
//                if (onShareClickListener != null) {
//                    onShareClickListener.onClick("微博");
//                }
//                setAnimationAndSharePlates(SHARE_MEDIA.SINA, imageSina);
//                break;
//            case R.id.lin_share_foot:
//            case R.id.rl_share:
//                shareDialogWindow.cancel();
//                break;
//            case R.id.copy:
//                if (onShareClickListener != null) {
//                    onShareClickListener.onClick("复制链接");
//                }
//                setAnimationAndSharePlates(null, imageCopy);
//                doCopy();
//                break;
//            default:
//                break;
//        }
//    }
//
//    private void doCopy() {
//        String shareurl = shareUrl;
//        if (isNotNull(shareurl)) {
//            copyOrderId(activity, null, shareurl);
//        } else {
//            showSnackbar(activity, "复制失败");
//        }
//    }
//
//    /**
//     * @param @param platform 分享的平台
//     * @param @param imageView 产生动画效果的Image
//     * @param @param flag 是否是友盟分享（true是友盟分享，false是调用系统默认的分享功能）
//     * @return void
//     * @throws
//     * @Title: setAnimationAndSharePlates
//     * @Description: TODO设置点击的动画和动画后跳转的分享平台
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    private void setAnimationAndSharePlates(final SHARE_MEDIA platform, ImageView imageView) {
//        PropertyValuesHolder pvhX1 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
//        PropertyValuesHolder pvhY1 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0, 1f);
//        PropertyValuesHolder pvhZ1 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0, 1f);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, pvhX1, pvhY1, pvhZ1);
//        objectAnimator.setDuration(200);
//        objectAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (null != platform) {
//                    performShare(platform);
//                }
//                shareDialogWindow.cancel();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//            }
//        });
//        objectAnimator.start();
//    }
//
//    /**
//     * @param @param platform
//     * @return void
//     * @throws
//     * @Title: performShare
//     * @Description: 自定义友盟分享操作的接口回调方法
//     */
//    private void performShare(SHARE_MEDIA platform) {
//        setShareContent();
//        mShareAction.setPlatform(platform).share();
//    }
//
//    /**
//     * 设置分享消息内容
//     *
//     * @return
//     * @功能描述 : 设置对应平台分享的内容
//     * baseShareContent 需要传入分享的平台
//     */
//    private void setShareContent() {
//        if (!TextUtils.isEmpty(title)) {
//            if (TextUtils.isEmpty(content) || "网页视图".equals(content)) {
//                content = title;
//            }
//        } else {
//            if (!TextUtils.isEmpty(content)) {
//                title = content;
//            }
//        }
//        UMImage umImage;
//        if (shareImage != null) {
//            umImage = shareImage;
//        } else {
//            umImage = new UMImage(activity, ZpLuxuryApi.API_HOST_SHAREIMG + "?id=" + UUID.randomUUID().toString());
//        }
//        UMWeb umWeb = new UMWeb(shareUrl);
//        umWeb.setTitle(title);//标题
//        umWeb.setDescription(content);//描述
//        umWeb.setThumb(umImage);//缩略图
//        mShareAction.withMedia(umWeb);
//    }
//
//    private Handler childHandler;
//
//    public void bindHandler(Handler childHandler) {
//        this.childHandler = childHandler;
//    }
//
//    private void sendSharePlatform(SHARE_MEDIA platform) {
//        switch (platform) {
//            case SINA:
//            case WEIXIN_CIRCLE:
//            case WEIXIN:
//            case QQ:
//                if (childHandler != null) {
//                    Message msg = new Message();
//                    msg.obj = platform;
//                    msg.what = 200;
//                    childHandler.sendMessage(msg);
//                }
//                break;
//            default:
//                break;
//        }
//    }
//}