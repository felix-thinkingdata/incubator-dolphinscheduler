package org.apache.dolphinscheduler.alert.utils;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * Created by zhoujin on 2018/11/15.
 */
public class HttpRequestUtil {
    private static CloseableHttpClient httpClient;
    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setDefaultMaxPerRoute(800);
        cm.setMaxTotal(1000);
        RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                .setConnectTimeout(60000)
                .setSocketTimeout(60000).build();
        httpClient  = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(globalConfig).build();
    }

    public static CloseableHttpClient getHttpClient(){
        return httpClient;
    }
}
