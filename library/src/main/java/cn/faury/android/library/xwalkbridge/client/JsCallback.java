/**
 *  安卓Hybrid开发桥接包
 *
 *  @author faury
 *
 *  版权所有：秋刀鱼
 *  Copyright (c) http://www.faury.cn
 */
package cn.faury.android.library.xwalkbridge.client;


import org.xwalk.core.XWalkView;

import java.lang.ref.WeakReference;
import java.util.Locale;

/**
 * js回调
 */
public class JsCallback {
    private static final String CALLBACK_JS_FORMAT = "javascript:%s.callback(%d, %d %s);";
    private int mIndex;
    private boolean mCouldGoOn;
    private WeakReference<XWalkView> mWebViewRef;
    private int mIsPermanent;
    private String mInjectedName;

    public JsCallback(XWalkView view, String injectedName, int index) {
        mCouldGoOn = true;
        mWebViewRef = new WeakReference<>(view);
        mInjectedName = injectedName;
        mIndex = index;
    }

    public void apply(Object... args) throws JsCallbackException {
        if (mWebViewRef.get() == null) {
            throw new JsCallbackException("the WebView related to the JsCallback has been recycled");
        }
        if (!mCouldGoOn) {
            throw new JsCallbackException("the JsCallback isn't permanent,cannot be called more than once");
        }
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(",");
            boolean isStrArg = arg instanceof String;
            if (isStrArg) {
                sb.append("\"");
            }
            sb.append(String.valueOf(arg));
            if (isStrArg) {
                sb.append("\"");
            }
        }
        String execJs = String.format(Locale.getDefault(),CALLBACK_JS_FORMAT, mInjectedName, mIndex, mIsPermanent, sb.toString());
        mWebViewRef.get().load(execJs,null);
        mCouldGoOn = mIsPermanent > 0;
    }

    public void setPermanent(boolean value) {
        mIsPermanent = value ? 1 : 0;
    }
}
