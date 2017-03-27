package com.king.baseproject.util;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.king.baseproject.R;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by king on 2017/3/24.
 * ImagerLoade初始化
 */

public class ImageLoaderUtil {
    public static DisplayImageOptions options;
    public static void initImagerLoader(Context context){
        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int cacheSize = 1024 * 1024 * memClass / 2;
        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).memoryCacheExtraOptions(480, 640)
                // default = device screen dimensions 缓存每个文件的最大长宽度
                .threadPoolSize(3)
                // default 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                // default 线程优先级
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // default
                .denyCacheImageMultipleSizesInMemory()
                // .discCacheFileCount(10)
                .memoryCache(new LruMemoryCache(cacheSize))
                .imageDownloader( new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout(5s),
                // readTimeout(30s)超时时间
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.page_1)
                .resetViewBeforeLoading(true)
                // default
                .showImageForEmptyUri(R.drawable.page_1)
                .showImageOnFail(R.drawable.page_1).cacheInMemory(false) // default
                .cacheOnDisk(true) // default
                .considerExifParams(false) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                .displayer(new FadeInBitmapDisplayer(300)) // default
                .handler(new Handler()) // default
                .build();
    }

}
