package com.king.baseproject.util;

import android.content.Context;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;


/**
 * Created by king on 2017/3/20.
 * 统计事件
 */

public class StatsUtil {
    /**
     * umeng初始化
     */
    //umeng  appkey
    private static final String umengKey = "58cf401f9f06fd74e2000af9";
    //推广渠道
    private static final String channelId = "google";

    private static final String eventId ="btn";
    public static void init(Context context){
        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(context, umengKey, channelId);
        MobclickAgent.startWithConfigure(config);

    }

    public static  void onResume(Context context){
        MobclickAgent.onResume(context);

    }
    public static void onPause(Context context){
        MobclickAgent.onPause(context);

    }


    public static void setBtnSessuon(Context context){
        MobclickAgent.onEvent(context, eventId);
    };
}
