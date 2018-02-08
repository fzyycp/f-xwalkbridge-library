package cn.faury.android.library.xwalkbridge.listen;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.webkit.ValueCallback;

import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

/**
 *
 */

public interface XWalkViewListener {

    /**
     * Tell the client to toggle fullscreen mode.
     * @param view
     * @param enterFullscreen
     */
    void onFullscreenToggled(XWalkView view, boolean enterFullscreen);

    /**
     * Notify the host application that a page has started loading.
     * @param view
     * @param url
     */
    void onPageLoadStarted(XWalkView view, String url);

    /**
     * Notify the host application that a page has stopped loading.
     * @param view
     * @param url
     * @param status
     */
    void onPageLoadStopped(XWalkView view, String url, XWalkUIClient.LoadStatus status);

    void onReceivedIcon(XWalkView view, String url, Bitmap icon);

    /**
     * Request display and focus for this XWalkView.
     * @param view
     */
    void onRequestFocus(XWalkView view);

    /**
     * Notify the host application of a change in the document title.
     * @param view
     * @param title
     */
    void onReceivedTitle(XWalkView view, String title);

    boolean onCreateWindowRequested(XWalkView view, XWalkUIClient.InitiateBy initiator, ValueCallback<XWalkView> callback);

    /**
     * Notify the host application that a key was not handled by the XWalkView.
     * @param view
     * @param event
     */
    void onUnhandledKeyEvent(XWalkView view, KeyEvent event);

    boolean onConsoleMessage(XWalkView view, String message, int lineNumber, String sourceId, XWalkUIClient.ConsoleMessageType messageType);
}
