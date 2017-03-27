package com.king.baseproject.activity;
import android.os.Bundle;
import com.king.baseproject.R;
import com.king.baseproject.common.GuidView.PageFrameLayout;
import com.king.baseproject.util.StatsUtil;


/**
 * Created by king on 2017/1/11.
 * 启动类
 */

public class StartActivity extends BaseActivity {
    /**
     * 延迟毫秒数
     */
    private PageFrameLayout pagefragmeng;
    private String url1;
    private String url2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url1 = "http://static.52mamahome.com/mamahome/ads/0222.jpg";
        url2 = "http://static.52mamahome.com/mamahome/ads/0222.jpg";
        StatsUtil.init(this);
        setContentView(R.layout.activity_statrt);
        pagefragmeng = (PageFrameLayout)findViewById(R.id.pagefragmeng);
        pagefragmeng.setUpViews(new int[]{
                    R.layout.wel_page1,
                    R.layout.wel_page2,
                    R.layout.wel_page3,
            }, R.mipmap.banner_on, R.mipmap.banner_off,this,url1,url2);
    }
}
