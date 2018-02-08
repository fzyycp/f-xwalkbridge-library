package cn.faury.android.library.xwalkbridge.client;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ValueCallback;

import org.xwalk.core.XWalkJavascriptResult;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

import cn.faury.android.library.xwalkbridge.listen.XWalkViewListener;


public class InjectedXWalkUIClient extends XWalkUIClient {
    /**
     * 默认桥接器名称
     */
    public static final String DEFAULT_INJECTED_NAME = "Activity";
    private static final String TAG = InjectedXWalkUIClient.class.getName();

    /**
     * JS调用Java
     */
    private JsCallJava mJsCallJava;

    private XWalkViewListener listener;

    /**
    /**
     * 构造函数
     * @param injectedName 对象名
     * @param injectedCls 对象实例
     */
    public InjectedXWalkUIClient(XWalkView view,String injectedName, Class injectedCls) {
        super(view);
        if (injectedName == null) {
            injectedName = DEFAULT_INJECTED_NAME;
        }
        this.mJsCallJava = new JsCallJava(injectedName, injectedCls);
    }

    /**
     * 构造函数
     * @param jsCallJava 注入对象
     */
    public InjectedXWalkUIClient(XWalkView view,JsCallJava jsCallJava) {
        super(view);
        this.mJsCallJava = jsCallJava;
    }

    /**
     * 构造函数
     * @param jsCallJava 注入对象
     */
    public InjectedXWalkUIClient(XWalkView view,JsCallJava jsCallJava,XWalkViewListener listener) {
        super(view);
        this.mJsCallJava = jsCallJava;
        this.listener = listener;
    }

    @Override
    public boolean onJsAlert(XWalkView view, String url, String message, XWalkJavascriptResult result) {
        result.cancel();
//        result.confirm();
        return false;
    }

    @Override
    public boolean onJsPrompt(XWalkView view, String url, String message, String defaultValue, XWalkJavascriptResult result) {
        result.confirmWithResult(mJsCallJava.call(view,message));
        return true;
    }

    @Override
    public void onFullscreenToggled(XWalkView view, boolean enterFullscreen) {
        super.onFullscreenToggled(view, enterFullscreen);
        Log.e(TAG, "onFullscreenToggled: "+enterFullscreen);
        if (this.listener != null) {
            this.listener.onFullscreenToggled(view,enterFullscreen);
        }
    }

    @Override
    public void onPageLoadStarted(XWalkView view, String url) {
        super.onPageLoadStarted(view, url);
        if (this.listener != null) {
            this.listener.onPageLoadStarted(view, url);
        }
    }

    @Override
    public void onPageLoadStopped(XWalkView view, String url, LoadStatus status) {
        super.onPageLoadStopped(view, url, status);
        if (this.listener != null) {
            this.listener.onPageLoadStopped(view, url, status);
        }
    }

    @Override
    public void onReceivedIcon(XWalkView view, String url, Bitmap icon) {
        super.onReceivedIcon(view, url, icon);
        if (this.listener != null) {
            this.listener.onReceivedIcon(view, url, icon);
        }
    }

    @Override
    public void onRequestFocus(XWalkView view) {
        super.onRequestFocus(view);
        if (this.listener != null) {
            this.listener.onRequestFocus(view);
        }
    }

    @Override
    public void onReceivedTitle(XWalkView view, String title) {
        super.onReceivedTitle(view, title);
        if (this.listener != null) {
            this.listener.onReceivedTitle(view, title);
        }
    }

    @Override
    public boolean onCreateWindowRequested(XWalkView view, InitiateBy initiator, ValueCallback<XWalkView> callback) {
        if (this.listener != null) {
            return this.listener.onCreateWindowRequested(view, initiator, callback);
        }
        return super.onCreateWindowRequested(view, initiator, callback);
    }

    @Override
    public void onUnhandledKeyEvent(XWalkView view, KeyEvent event) {
        super.onUnhandledKeyEvent(view, event);
        if (this.listener != null) {
            this.listener.onUnhandledKeyEvent(view, event);
        }
    }

    @Override
    public boolean onConsoleMessage(XWalkView view, String message, int lineNumber, String sourceId, ConsoleMessageType messageType) {
        if (this.listener != null) {
            return this.listener.onConsoleMessage(view, message, lineNumber, sourceId, messageType);
        }
        return super.onConsoleMessage(view, message, lineNumber, sourceId, messageType);
    }
}
