package com.rajiv.a300269668.newsapp;

import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by mom on 2018-06-06.
 */

public class onViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView v, String url)
    {
        v.loadUrl(url);
        return true;
    }
}