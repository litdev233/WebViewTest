package net.litdev.webviewtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class WebHtmlActivity extends Activity {

    private final static String TAG = "WebHtmlActivity";
    private ProgressBar pb_bar;
    private WebView wv_view;
    private TextView tv_back;
    private TextView tv_close;

    //报文头
    private Map<String, String> webViewHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_html);

        initView();

    }

    private void initView() {
        wv_view = (WebView) findViewById(R.id.wv_view);
        pb_bar = (ProgressBar) findViewById(R.id.pb_bar);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_close = (TextView) findViewById(R.id.tv_close);
        //添加报文头数据
        webViewHead = new HashMap<String, String>();
        webViewHead.put("APP", "V1.0");

        WebSettings webSetting = wv_view.getSettings();
        //启用JS
        webSetting.setJavaScriptEnabled(true);
        //设置是否可以缩放
        webSetting.setSupportZoom(false);
        //修改User_Agent
        webSetting.setUserAgentString("Mozilla/5.0 (Linux; Android 5.1; Litdev For PRO 5 Build/LMY47D) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/40.0.2214.114 Mobile Safari/537.36");
        //webSetting.setBuiltInZoomControls(true);

        wv_view.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        wv_view.setHorizontalScrollBarEnabled(false);
        //设置网页缩放
        //wv_view.setInitialScale(70);

        wv_view.setWebViewClient(new MyCustomWebViewClient());
        wv_view.setWebChromeClient(new MycustomWebChromeClient());

        wv_view.loadUrl("https://github.com/litdev233/", webViewHead);
    }

    private class MycustomWebChromeClient extends WebChromeClient
    {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            //Log.i(TAG,"网站的标题是："+title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress == 100){
                pb_bar.setVisibility(View.GONE);
            }
            pb_bar.setProgress(newProgress);
        }

        /**
         * 处理网页中的Alert
         * @param view
         * @param url
         * @param message
         * @param result
         * @return
         */
        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            AlertDialog.Builder builder = new AlertDialog.Builder(WebHtmlActivity.this);
            builder.setTitle("提醒");
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
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
        //点击链接继续在当前browser中响应，而不是新开Android的系统browser中响应该链接
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url,webViewHead);
            pb_bar.setProgress(0);
            pb_bar.setVisibility(View.VISIBLE);
            return true;
        }

        //// 重写此方法可以让webview处理https请求。
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return super.shouldOverrideKeyEvent(view, event);
        }

        //处理在浏览器中的按键事件
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        //开始载入
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //获取url信息
            //Log.i(TAG,"请求的HOST："+Uri.parse(url).getHost());
            wv_view.setVisibility(View.GONE);
            if(wv_view.canGoBack()){
                tv_back.setVisibility(View.VISIBLE);
            }else{
                tv_back.setVisibility(View.GONE);
            }

        }

        //页面载入完成
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            wv_view.setVisibility(View.VISIBLE);
        }



        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            wv_view.loadUrl("file:///android_asset/404.html");
            Log.i(TAG,"加载失败："+error);
            wv_view.setVisibility(View.GONE);
        }
    }

    //后退
    public void TVBack(View v) {
        if (wv_view.canGoBack()) {
            wv_view.goBack();
        } else {
            finish();
        }
    }

    //关闭
    public void TVClose(View v) {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && wv_view.canGoBack()){
            wv_view.goBack();
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}
