package com.king.baseproject.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.king.baseproject.R;

/**
 * Created by king on 2017/1/11.
 * activty 跳转工具类
 */

public class ActivityJump {
    /**
     * activity跳转
     *  @param clz
     */
    public static void JumpActivity(Activity activity ,Class clz){
        Intent intent = new Intent(activity,clz);
        activity.startActivity(intent);
        //activity 跳转动画必须在startActivity 或者finish之后调用
        activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }
    /**
     * activity 带参数跳转
     * @param activity
     * @param clz
     * @param bundle
     */
    public static void JumpActivityWithBundle(Activity activity , Class clz, Bundle bundle){
        Intent intent = new Intent(activity,clz);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        //activity 跳转动画必须在startActivity 或者finish之后调用
        activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * activity 跳转回传参数
     * @param activity
     * @param clz
     * @param bundle
     * @param requestCode
     */
    public static void JumpActivityWithBundleForResult(Activity activity , Class clz, Bundle bundle,int requestCode){
        Intent intent = new Intent(activity,clz);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent,requestCode);
        //activity 跳转动画必须在startActivity 或者finish之后调用
        activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }
}
