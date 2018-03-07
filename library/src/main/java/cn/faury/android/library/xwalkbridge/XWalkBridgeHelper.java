package cn.faury.android.library.xwalkbridge;

import android.view.View;

import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cn.faury.android.library.xwalkbridge.client.InjectedXWalkResourceClient;
import cn.faury.android.library.xwalkbridge.client.InjectedXWalkUIClient;


/**
 * 通用设置webview相关属性
 *
 * @author faury
 */
public class XWalkBridgeHelper {

    public static final String DEFAULT_INJECTED_NAME = "Activity";

    /**
     * 初始化配置
     */
    public static final void initPreferences() {
//        // The key string to allow/disallow having universal access from file origin.
//        // 置是否允许通过file url加载的Javascript可以访问其他的源,包括其他的文件和http,https等其他的源
//        XWalkPreferences.setValue(XWalkPreferences.ALLOW_UNIVERSAL_ACCESS_FROM_FILE, true);
//
//        // The key string to allow/disallow javascript to open window automatically.
//        XWalkPreferences.setValue(XWalkPreferences.JAVASCRIPT_CAN_OPEN_WINDOW, false);
//
////        //添加对javascript支持
////        XWalkPreferences.setValue("enable-javascript", true);
//        XWalkPreferences.setValue(XWalkPreferences.SUPPORT_MULTIPLE_WINDOWS, true);
    }

    /**
     * 桥接方式通用配置设置
     *
     * @param webView webview对象
     */
    public static void setWebView(XWalkView webView) {

        if (webView == null) {
            return;
        }
//        // webview设置
//        webView.requestFocus();
//        webView.requestFocusFromTouch();
//        webView.setFocusable(true);
//        webView.setFocusableInTouchMode(true);
//        // 停用缓存
//        webView.setDrawingCacheEnabled(false);
//        webView.getNavigationHistory().clear();
//        webView.clearCache(true);
//
//        //设置滑动样式。。。
        webView.setHorizontalScrollBarEnabled(false);
//        webView.setVerticalScrollBarEnabled(false);
//        webView.setScrollBarStyle(XWalkView.SCROLLBARS_OUTSIDE_INSET);
//        webView.setScrollbarFadingEnabled(true);
//        webView.setInitialScale(1);
//
        // 屏蔽掉长按事件 因为webview长按时将会调用系统的复制控件
        webView.setLongClickable(false);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                return true;
            }
        });
//        webView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.requestFocus();
//                return false;
//            }
//        });
//
//        //获取setting
//        XWalkSettings mMSettings = webView.getSettings();
//        //支持空间导航
//        mMSettings.setSupportSpatialNavigation(false);
//        mMSettings.setBuiltInZoomControls(false);
//        mMSettings.setSupportZoom(false);
    }

    /**
     * 初始化WebView
     *
     * @param webView        webview对象
     * @param uiClient       XWalkUIClient
     * @param resourceClient XWalkResourceClient
     */
    public static void injectWebView(@Nonnull XWalkView webView
            , @Nullable XWalkUIClient uiClient
            , @Nullable XWalkResourceClient resourceClient) {
        if (webView == null) {
            return;
        }
        setWebView(webView);
        if (uiClient != null) {
            webView.setUIClient(uiClient);
        }
        if (resourceClient != null)
            webView.setResourceClient(resourceClient);
    }

    /**
     * 初始化WebView
     *
     * @param webView      webview对象
     * @param injectedName 注入对象名称
     * @param injectedCls  注入java类
     * @param localMode    本地模式
     */
    public static void injectWebView(@Nonnull XWalkView webView, @Nullable String injectedName, @Nonnull Class injectedCls, boolean localMode) {
        injectWebView(webView
                , new InjectedXWalkUIClient(webView, injectedName, injectedCls)
                , new InjectedXWalkResourceClient(webView, localMode));
    }

    /**
     * 初始化WebView
     *
     * @param webView     初始化
     * @param injectedCls 注入java类
     * @param localMode   本地模式
     */
    public static void injectWebView(XWalkView webView, Class injectedCls, boolean localMode) {
        injectWebView(webView, DEFAULT_INJECTED_NAME, injectedCls, localMode);
    }

    /**
     * 释放webview资源
     *
     * @param webView 当前webview
     */
    public static void destoryWebView(XWalkView webView) {
        if (webView == null) {
            return;
        }
        webView.load("about:blank", null);
        webView.onDestroy();
    }

    /**
     * 启动WebView调试模式，可以在Chrome浏览器中调试
     *
     * @param enable 是否启动
     */
    public static void enableDebugging(boolean enable) {
        // The key string to enable/disable remote debugging.
        // 开启调式,支持谷歌浏览器调式
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, enable);
    }
}
