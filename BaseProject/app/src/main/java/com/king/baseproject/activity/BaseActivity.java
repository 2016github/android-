package com.king.baseproject.activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.baseproject.R;
import com.king.baseproject.util.StatsUtil;
import com.king.baseproject.util.ViewBindUtil;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.RxLifecycle;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by king on 2017/1/11.
 *使用注意问题
 * 需要在项目主题中设置   <item name="android:windowContentOverlay">@null</item>不要阴影
 *   <item name="android:actionBarSize">60dp</item> 高度
 *兼容5.1以上版本
 * 添加 if(Build.VERSION.SDK_INT>=21){getSupportActionBar().setElevation(0);代码}
 *
 *
 */
public class BaseActivity extends FragmentActivity implements ActivityLifecycleProvider{
    private TextView text_title;
    private ImageView img_back;
    private TextView rightText;
    @CallSuper
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,  ActionBar.LayoutParams.MATCH_PARENT, Gravity.NO_GRAVITY);
        View viewTitleBar = getLayoutInflater().inflate(R.layout.actionbar_title, null);
        text_title = (TextView) viewTitleBar.findViewById(R.id.text_title);
        img_back = (ImageView) viewTitleBar.findViewById(R.id.img_back);
        rightText = (TextView) viewTitleBar.findViewById(R.id.rightText);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              RightOnclick();
            }


        });
    }

    /**
     * 右边文字点击事件
     */
    public void RightOnclick() {

    }
    /**
     * 设置title
     * @param title
     */
    public void setTitle(String title){
        text_title.setText(title);
    }

    /**
     * 设置右边文字
     * @param righttext
     */
    public void setRightText(String righttext){
        rightText.setText(righttext);
    }

    /**
     * 设置右边文字颜色
     * @param colorid
     */
    public void setRightTextColor(int  colorid){
        rightText.setTextColor(colorid);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ViewBindUtil.bindViews(this, getWindow().getDecorView());
    }
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ViewBindUtil.bindViews(this, getWindow().getDecorView());
    }


    public final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @NonNull
    @Override
    public Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @NonNull
    @Override
    public <T> Observable.Transformer<T, T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @NonNull
    @Override
    public <T> Observable.Transformer<T, T> bindToLifecycle() {
        return RxLifecycle.bindActivity(lifecycleSubject);
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        StatsUtil.onResume(this);
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
        StatsUtil.onPause(this);
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }
}
