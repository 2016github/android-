package com.king.baseproject.network;

import android.util.Log;

import com.king.baseproject.application.BaseApplication;
import com.king.baseproject.util.MYToast;

import rx.Subscriber;

/**
 * Created by a on 2016/5/6.
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

        Log.e("Tag","onCompleted");
//        ProgressDialogUtil.dismiss();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        Log.e("Tag","getMessage"+e.getMessage());
        _onError(e.getMessage());
//        ProgressDialogUtil.dismiss();
        if (!NetUtils.isConnected(BaseApplication.getContextObject())) {
            MYToast.show("请求失败，请检查网络!");
            return;
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    public abstract void _onNext(T t);

    public abstract void _onError(String msg);
}
