package com.zyf.italker.common;

public class Common {

    /**
     * 一些不可变的永恒的参数
     * 通常用于一些配置
     */
    public interface Constance {
        //手机号的正则,位手机号
        String REGES_MOBILE = "[1][3,4,5,7,8][0-9]{9}$";

        //基础的网络请求地址 外网用这个
        String API_URL = "http://192.168.43.66:8080/api/";

        //插路由器用这个
        //String API_URL = "http://10.98.45.194:8080/api/";
    }

}