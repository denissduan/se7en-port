package com.se7en.common.util.btsd;

import java.util.Map;

/**
 * Created by Administrator on 17-5-21.
 */
public class BtsdHttpResult {

    public final static BtsdHttpResult EMPTY_ENTITY = new BtsdHttpResult();

    private String code;

    private String data;

    private Map<String,String> cookie;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Map<String, String> getCookie() {
        return cookie;
    }

    public void setCookie(Map<String, String> cookie) {
        this.cookie = cookie;
    }

    public BtsdHttpResult(String code, String data, Map<String, String> cookie) {
        this.code = code;
        this.data = data;
        this.cookie = cookie;
    }

    public BtsdHttpResult() {
    }

    public static BtsdHttpResult valueOf(String code, String data, Map<String, String> cookie){
        return new BtsdHttpResult(code, data, cookie);
    }
}
