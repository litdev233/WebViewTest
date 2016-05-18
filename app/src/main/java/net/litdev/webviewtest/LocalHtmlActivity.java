package net.litdev.webviewtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class LocalHtmlActivity extends Activity {

    private ProgressBar pb_bar;
    private WebView wv_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_html);

        initView();

    }

    private void initView() {
        pb_bar = (ProgressBar) findViewById(R.id.pb_bar);
        wv_view = (WebView) findViewById(R.id.wv_view);

        WebSettings webSetting = wv_view.getSettings();
        // 设置是否可以缩放
        webSetting.setJavaScriptEnabled(true);
        webSetting.setSupportZoom(false);
        webSetting.setUserAgentString("Mozilla/5.0 (Linux; Android 5.1; Litdev For PRO 5 Build/LMY47D) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/40.0.2214.114 Mobile Safari/537.36");
        wv_view.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        wv_view.setHorizontalScrollBarEnabled(false);

        wv_view.setWebChromeClient(new MycustomWebChromeClient());
        wv_view.setWebViewClient(new MyCustomWebViewClient());

        //暴露JAVA方法供前端JS调用
        wv_view.addJavascriptInterface(this,"test");
        //测试js调用java代码
        wv_view.loadUrl("file:///android_asset/test.html");

    }

    @JavascriptInterface
    public void scustomFunction(final String val){
        Toast.makeText(this,"JS调用了JAVA的函数,参数："+val,Toast.LENGTH_SHORT).show();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("data","JS调用了JAVA的函数,参数："+val);
            }
        });
    }

    private class MycustomWebChromeClient extends WebChromeClient
    {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            pb_bar.setProgress(newProgress);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            //构造通知
            AlertDialog.Builder builder = new AlertDialog.Builder(LocalHtmlActivity.this);
            builder.setTitle("提醒");
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            });
            builder.setCancelable(false);
            builder.create().show();
            return  true;
        }
    }
    private class MyCustomWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return  true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            wv_view.setVisibility(View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            wv_view.setVisibility(View.VISIBLE);
        }


    }

    /**
     * 后退键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 希望浏览的网 页回退而不是退出浏览器
        if (keyCode == KeyEvent.KEYCODE_BACK && wv_view.canGoBack()) {
            wv_view.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
