package com.se7en.common.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

/**
 * Created by Administrator on 2016/8/22.
 */
public final class HttpClientUtil {

    private HttpClientUtil(){
    }

    private static CloseableHttpClient createCloseableHttpClient(Map<String, String> cookieMap, CookieStore cookieStore, boolean isSSL) {
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setUserAgent("Mozilla/5.0(Windows;U;Windows NT 5.1;en-US;rv:0.9.4)");
        //注入cookie
        if(cookieMap != null && cookieMap.isEmpty() == false){
            for(Map.Entry<String,String> ckEty : cookieMap.entrySet()){
                BasicClientCookie cookie = new BasicClientCookie(ckEty.getKey(), ckEty.getValue());
                cookieStore.addCookie(cookie);
            }
        }
        builder.setDefaultCookieStore(cookieStore);

        //
        if(isSSL == true){
            try {
                SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
                builder.setSSLContext(sslContext);

                HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

                SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
                Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", sslSocketFactory)
                        .build();

                PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
                builder.setConnectionManager(connMgr);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }

        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).build();//标准Cookie策略
        builder.setDefaultRequestConfig(requestConfig);
        return builder.build();
    }

    public static Object[] get(String url, Map<String,String> cookieMap, String referer, boolean isSSL){
        Object[] result = ArrayUtils.EMPTY_OBJECT_ARRAY;

        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient client = createCloseableHttpClient(cookieMap, cookieStore, isSSL);
        try {
            // Do not do this in production!!!
            /*HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

            SchemeRegistry registry = new SchemeRegistry();
            SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
            socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
            registry.register(new Scheme("https", socketFactory, 443));
            SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
            DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());*/

//            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            if(StringUtils.isNotEmpty(referer)){
                httpGet.setHeader("referer", referer);
            }

            HttpResponse httpResponse = client.execute(httpGet);
            HttpEntity httpent = httpResponse.getEntity();

            String code = String.valueOf(httpResponse.getStatusLine()
                    .getStatusCode());

            // 获取cookie
            List<Cookie> cookies = cookieStore.getCookies();
            HashMap<String, String> map = new HashMap<String, String>();
            if (!cookies.isEmpty()) {
                for (int i = 0; i < cookies.size(); i++) {
                    map.put(cookies.get(i).getName(), cookies.get(i).getValue());
                }
            }

            StringBuffer sb = getResultStr(httpent);

            String retJsonStr = sb.toString();
            result = new Object[]{retJsonStr, code, map};
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static Object[] get(String url, Map<String,String> cookieMap, String referer){
        return get(url, cookieMap, referer, false);
    }

    public static Object[] get(String url){
        return get(url, null, null, false);
    }

    public static Object[] getWithSSL(String url){
        return get(url, null, null, true);
    }

    public static Object[] post(String url, Map<String,String> cookieMap, String referer, Map<String,String> params, boolean isSSL){
        Object[] ret = ArrayUtils.EMPTY_OBJECT_ARRAY;

        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient client = createCloseableHttpClient(cookieMap, cookieStore, isSSL);
        try {
            // Do not do this in production!!!
            /*HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

            DefaultHttpClient client = new DefaultHttpClient();

            SchemeRegistry registry = new SchemeRegistry();
            SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
            socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
            registry.register(new Scheme("https", socketFactory, 443));
            SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
            DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());*/

            // post 请求
//            DefaultHttpClient client = new DefaultHttpClient();
            //传参
            HttpPost httpPost = new HttpPost(url);
            if(params != null && params.isEmpty() == false){
                List<BasicNameValuePair> paramList = new LinkedList<>();
                for(Map.Entry<String,String> paramEty : params.entrySet()){
                    paramList.add(new BasicNameValuePair(paramEty.getKey(),paramEty.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(paramList));
            }

            if(StringUtils.isNotEmpty(referer)){
                httpPost.setHeader("referer", referer);
            }
//            postjson.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");

            // 获得返回的json数据包
            HttpResponse httpResponse = client.execute(httpPost);
            HttpEntity httpent = httpResponse.getEntity();

            String code = String.valueOf(httpResponse.getStatusLine()
                    .getStatusCode());

            // 获取cookie
            List<Cookie> cookies = cookieStore.getCookies();
            HashMap<String, String> cookieRet = new HashMap<String, String>();
            if (!cookies.isEmpty()) {
                for (int i = 0; i < cookies.size(); i++) {
                    cookieRet.put(cookies.get(i).getName(), cookies.get(i).getValue());
                }
            }

            StringBuffer sb = getResultStr(httpent);

            ret = new Object[]{sb.toString(), code, cookieRet};
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }

    public static Object[] post(String url, Map<String,String> cookieMap, String referer, Map<String,String> params){
        return post(url, cookieMap, referer, params, false);
    }

    public static Object[] post(String url, Map<String,String> params){
        return post(url, null, null, params, false);
    }

    public static Object[] postWithSSL(String url, Map<String,String> params){
        return post(url, null, null, params, true);
    }

    /**
     * 拼接请求结果字符串
     * @param httpent
     * @return
     * @throws IOException
     */
    private static StringBuffer getResultStr(HttpEntity httpent) throws IOException {
        StringBuffer sb = new StringBuffer();
        if (httpent != null) {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    httpent.getContent(), "UTF-8"));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        }
        return sb;
    }
}
