package com.king.baseproject.common.GuidView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.king.baseproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author king
 * 引导和广告页
 */
public class PageFrameLayout extends FrameLayout implements ViewPager.OnPageChangeListener {
    private SharedPreferences sp;
    private boolean isFirst;


    private List<PageFragment> fragments = new ArrayList<>();
    private ImageView[] iv_vp = null;
    private LinearLayout dot_ll;
    private int dot_on, dot_off;
    private FragmentActivity activity;
    public PageFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    /**
     * dpתpx
     */
    public int dip2px(Context ctx, float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 设置资源文件并初始化控件
     * @param layoutIds Viewpager资源
     * @param dot_on  点
     * @param dot_off 点
     * @param activity 类
     * @param url1  广告url
     * @param url2  广告跳荘url
     */
    public void setUpViews(int layoutIds[], int dot_on, int dot_off, FragmentActivity activity,String url1,String url2) {
        sp = activity.getSharedPreferences("isFirst",Context.MODE_PRIVATE);
        isFirst = sp.getBoolean("isFirst",true);
        if (isFirst){
            //加载引导页
            this.dot_on = dot_on;
            this.dot_off = dot_off;
            this.activity = activity;
            iv_vp = new ImageView[layoutIds.length];
            dot_ll = new LinearLayout(getContext());
            LayoutParams params = new LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    dip2px(getContext(), 35));
            dot_ll.setGravity(Gravity.CENTER);
            params.gravity = Gravity.BOTTOM;
            dot_ll.setLayoutParams(params);
            dot_ll.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(8, 0, 8, 0);

            for (int i = 0; i < layoutIds.length; i++) {
                PageFragment pageFragment = new PageFragment();
                Bundle args = new Bundle();
                args.putInt("index", i);
                args.putInt("count", layoutIds.length);
                args.putInt("layoutId", layoutIds[i]);
                pageFragment.setArguments(args);
                fragments.add(pageFragment);

                ImageView view = new ImageView(getContext());
                view.setImageResource(dot_on);
                view.setLayoutParams(lp);
                iv_vp[i] = view;
                dot_ll.addView(iv_vp[i]);
            }

            setSelectVp(0);
            ViewPager viewPager = new ViewPager(getContext());
            viewPager.setId(R.id.id_page);
            viewPager.setAdapter(new PageFragmentAdapter(activity.getSupportFragmentManager(), fragments));
            viewPager.addOnPageChangeListener(this);
            addView(viewPager);
            addView(dot_ll);

            SharedPreferences.Editor editor=sp.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
        }else {
            //加载广告页
            AdvertView adview = new AdvertView(getContext());
            View view = adview.setview(url1,url2);
            addView(view);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == fragments.size() - 1) {
            dot_ll.setAlpha(0);
        } else {
            dot_ll.setAlpha(1);
        }

    }

    @Override
    public void onPageSelected(int position) {
        setSelectVp(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * 切换轮播图点
     *
     * @param index
     */
    public void setSelectVp(int index) {
        for (int i = 0; i < iv_vp.length; i++) {
            if (i == index) {
                iv_vp[i].setImageResource(dot_on);
            } else {
                iv_vp[i].setImageResource(dot_off);
            }
        }
    }
}
