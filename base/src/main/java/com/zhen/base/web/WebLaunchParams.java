//package com.zhen.base.web;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.zhenpin.luxurystore.model.WebViewHeaders;
//
//import java.io.Serializable;
//
//public class WebLaunchParams implements Serializable, Parcelable {
//    private String titleTxt;
//    /**
//     * 加载链接
//     */
//    private String visitUrl;
//    private String shareUrl;
//    private String helpUrl;
//    private boolean isActivity;
//    private boolean isShare;
//    private boolean isHelp;
//    private boolean isTitleBarGone;
//    private boolean isTransparentFrom;
//    /**
//     * 页面来源
//     */
//    private int fromId;
//    /**
//     * H5请求头
//     */
//    private WebViewHeaders webViewHeaders;
//
//    public String getTitleTxt() {
//        return titleTxt;
//    }
//
//    public void setTitleTxt(String titleTxt) {
//        this.titleTxt = titleTxt;
//    }
//
//    public String getVisitUrl() {
//        return visitUrl;
//    }
//
//    public void setVisitUrl(String visitUrl) {
//        this.visitUrl = visitUrl;
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
//    public String getHelpUrl() {
//        return helpUrl;
//    }
//
//    public void setHelpUrl(String helpUrl) {
//        this.helpUrl = helpUrl;
//    }
//
//    public boolean isActivity() {
//        return isActivity;
//    }
//
//    public void setActivity(boolean activity) {
//        isActivity = activity;
//    }
//
//    public boolean isShare() {
//        return isShare;
//    }
//
//    public void setShare(boolean share) {
//        isShare = share;
//    }
//
//    public boolean isHelp() {
//        return isHelp;
//    }
//
//    public void setHelp(boolean help) {
//        isHelp = help;
//    }
//
//    public boolean isTitleBarGone() {
//        return isTitleBarGone;
//    }
//
//    public void setTitleBarGone(boolean titleBarGone) {
//        isTitleBarGone = titleBarGone;
//    }
//
//    public boolean isTransparentFrom() {
//        return isTransparentFrom;
//    }
//
//    public void setTransparentFrom(boolean transparentFrom) {
//        isTransparentFrom = transparentFrom;
//    }
//
//    public int getFromId() {
//        return fromId;
//    }
//
//    public void setFromId(int fromId) {
//        this.fromId = fromId;
//    }
//
//    public WebViewHeaders getWebViewHeaders() {
//        return webViewHeaders;
//    }
//
//    public void setWebViewHeaders(WebViewHeaders webViewHeaders) {
//        this.webViewHeaders = webViewHeaders;
//    }
//
//    public WebLaunchParams() {
//    }
//
//    private WebLaunchParams(Parcel in) {
//        titleTxt = in.readString();
//        visitUrl = in.readString();
//        shareUrl = in.readString();
//        helpUrl = in.readString();
//        isActivity = in.readByte() != 0;
//        isShare = in.readByte() != 0;
//        isHelp = in.readByte() != 0;
//        isTitleBarGone = in.readByte() != 0;
//        isTransparentFrom = in.readByte() != 0;
//        fromId = in.readInt();
//    }
//
//    public static final Creator<WebLaunchParams> CREATOR = new Creator<WebLaunchParams>() {
//        @Override
//        public WebLaunchParams createFromParcel(Parcel in) {
//            return new WebLaunchParams(in);
//        }
//
//        @Override
//        public WebLaunchParams[] newArray(int size) {
//            return new WebLaunchParams[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(titleTxt);
//        dest.writeString(visitUrl);
//        dest.writeString(shareUrl);
//        dest.writeString(helpUrl);
//        dest.writeByte((byte) (isActivity ? 1 : 0));
//        dest.writeByte((byte) (isShare ? 1 : 0));
//        dest.writeByte((byte) (isHelp ? 1 : 0));
//        dest.writeByte((byte) (isTitleBarGone ? 1 : 0));
//        dest.writeByte((byte) (isTransparentFrom ? 1 : 0));
//        dest.writeInt(fromId);
//    }
//}