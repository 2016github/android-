package com.king.baseproject.common.GuidView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.docusign.esign.model.Text;
import com.king.baseproject.R;
import com.king.baseproject.activity.MainActivity;
import com.king.baseproject.util.ActivityJump;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by king on 2017/3/24.
 */

public class AdvertView extends RelativeLayout{
    private View view;
    private TextView text;
    private LayoutInflater inflater;
    private int time=6;
    private Timer timer;
    private Activity activity;

    private ImageView img_advert;
    public AdvertView(Context context) {
        super(context);
        Log.e("Tag","AdvertView");
        this.activity = (Activity) context;
        inflater = LayoutInflater.from(context);
    }

    /**
     *
     * @param Url1 广告页地址
     * @param Url2 跳转地址
     */
    public  View setview(String Url1,String Url2){
        view = inflater.inflate(R.layout.advert_view,null);
        img_advert = (ImageView) view.findViewById(R.id.img_advert);
        text = (TextView)view.findViewById(R.id.text);
        text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                ActivityJump.JumpActivity(activity, MainActivity.class);
                activity.finish();
            }
        });
        ImageLoader.getInstance().displayImage(Url1, img_advert, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                text.setVisibility(View.VISIBLE);
                RunTimer();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        return view;
    }

    public void RunTimer(){
        Log.e("Tag","RunTimer ==");
        timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run(){
                time--;
                Message msg=handler.obtainMessage();
                msg.what=1;
                handler.sendMessage(msg);
                Log.e("Tag","time =="+time);
            }
        };
        timer.schedule(task,0,1000);
    }

    private Handler handler =new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("Tag","handler ==");
                    if(time>=0){
                        text.setText(time+"");
                    }else{
                        ActivityJump.JumpActivity(activity, MainActivity.class);
                        activity.finish();
                        timer.cancel();
                    }

                    break;


                default:
                    break;
            }

        }
    };

}
