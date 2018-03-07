package cn.faury.android.library.xwalkbridge.client;

import android.util.Log;
import android.widget.Toast;

import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

public class InjectedXWalkResourceClient extends XWalkResourceClient {

    /**
     * 输出TAG
     */
    private static final String TAG = InjectedXWalkResourceClient.class.getName();

    /**
     * 本地模式
     */
    private boolean localMode = false;

    /**
     * 构造函数
     *
     * @param view webview对象
     */
    public InjectedXWalkResourceClient(XWalkView view) {
        super(view);
    }

    /**
     * 构造函数
     *
     * @param view    webview对象
     * @param isLocal 本地模式
     */
    public InjectedXWalkResourceClient(XWalkView view, boolean isLocal) {
        super(view);
        this.localMode = isLocal;
    }

    @Override
    public void onReceivedLoadError(XWalkView view, int errorCode, String description, String failingUrl) {
        if(localMode) {
            Toast.makeText(view.getContext(), "软件安装包不完整，请卸载后重新安装！", Toast.LENGTH_LONG).show();
            Log.e(TAG, String.format("onReceivedLoadError: errorCode=%s,description=%s,failingUrl=%s", errorCode, description, failingUrl));
        }else {
            super.onReceivedLoadError(view, errorCode, description, failingUrl);
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
        if (localMode && url != null && url.trim().toLowerCase().startsWith("http")) {
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }
}
