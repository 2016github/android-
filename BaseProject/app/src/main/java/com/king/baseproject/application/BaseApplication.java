package com.king.baseproject.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.king.baseproject.util.ImageLoaderUtil;

/**
 * Created by king on 2017/1/11.
 */

public class BaseApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ImageLoaderUtil.initImagerLoader(context);
    }

    public static Context getContextObject() {
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
