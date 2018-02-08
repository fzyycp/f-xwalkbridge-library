package cn.faury.android.library.xwalkbridge.client;

import android.util.Log;
import android.widget.Toast;

import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

public class InjectedXwalkResourceClient extends XWalkResourceClient {
    /**
     * JS调用Java
     */
    private JsCallJava mJsCallJava;


    public static final String TAG = InjectedXwalkResourceClient.class.getName();

    public InjectedXwalkResourceClient(XWalkView view,JsCallJava jsCallJava) {
        super(view);
        this.mJsCallJava = jsCallJava;
//        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    public void onProgressChanged(XWalkView view, int progressInPercent) {

//        if(swipeRefreshLayout!=null) {
//            if (progressInPercent == 100) {
//                swipeRefreshLayout.setRefreshing(false);
//            } else if (!swipeRefreshLayout.isRefreshing()) {
//                swipeRefreshLayout.setRefreshing(true);
//            }
//        }

        super.onProgressChanged(view, progressInPercent);
    }

    @Override
    public void onReceivedLoadError(XWalkView view, int errorCode, String description, String failingUrl) {
        Toast.makeText(view.getContext(),"软件安装包不完整，请卸载后重新安装！",Toast.LENGTH_LONG).show();
        Log.e(TAG, String.format("onReceivedLoadError: errorCode=%s,description=%s,failingUrl=%s", errorCode,description,failingUrl));
//        super.onReceivedLoadError(view, errorCode, description, failingUrl);
    }

    @Override
    public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
//        return super.shouldOverrideUrlLoading(view, url);
        if(url!=null && url.trim().toLowerCase().startsWith("http")){
            return true;
        }
        return super.shouldOverrideUrlLoading(view,url);
    }
}
