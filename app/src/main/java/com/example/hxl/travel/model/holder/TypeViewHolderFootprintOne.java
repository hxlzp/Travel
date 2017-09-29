package com.example.hxl.travel.model.holder;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.hxl.travel.R;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by hxl on 2017/8/18 0018 at haichou.
 */

public class TypeViewHolderFootprintOne extends TypeViewHolder<List<String>> {

    private final WebView webView;

    public TypeViewHolderFootprintOne(View itemView) {
        super(itemView);
        webView = ButterKnife.findById(itemView,R.id.web_item);
    }

    @Override
    public void bindViewHolder(final List<String> datas, final int position) {
        WebSettings settings = webView.getSettings();
        /*开启javascript*/
        settings.setJavaScriptEnabled(true);
        /*不缩放*/
        settings.setSupportZoom(false);
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(false);
        /*缓存*/
        settings.setAppCacheEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);
        settings.setUseWideViewPort(true);
        webView.loadUrl(datas.get(position));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(datas.get(position));
                return super.shouldOverrideUrlLoading(view, request);
            }

        });
        webView.setWebChromeClient(new WebChromeClient() {

        });
    }
}
