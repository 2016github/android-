package com.king.baseproject.network;
import android.util.Log;

import com.king.baseproject.application.BaseApplication;
import com.king.baseproject.network.interfaces.RequestData;
import com.king.baseproject.util.MYToast;

import java.io.File;
import java.util.Map;
import java.util.Set;
import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by a on 2016/5/4.
 */
public class HttpUtil {
    public static final String w3c_url = "http://www.w3school.com.cn";
    public static final String imagepp_url = "http://apicn.imageplusplus.com";
    public static final String url_API = "http://122.114.38.95:8857";
    public static final String url_weather = "http://wthrcdn.etouch.cn";
//    public static final String  Url = "http://192.168.1.10:8880/booking-api/";
    public static final String  Url = "http://192.168.1.10:8080/mamahome_app/";
    public static final String url_time = "http://api.k780.com:88";

    private static Retrofit getRetrofit(String url) {
        long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MiB
        String cacheFile = BaseApplication.getContextObject().getCacheDir()+"/http";
        Cache cache = new Cache(new File(cacheFile), SIZE_OF_CACHE);
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(CachingControlInterceptor.REWRITE_RESPONSE_INTERCEPTOR)
                .addInterceptor(CachingControlInterceptor.REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
                .cache(cache)
                .build();
        return new Retrofit.Builder().baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static RequestData getRequest(){
        return getRetrofit(Url).create(RequestData.class);
    };
    public static RequestBody getRequestFileBody(Map<String, File> map) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Set<String> keyset = map.keySet();
        for (String key : keyset) {
            File file = map.get(key);
            builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }
        return builder.build();
    }
    public static MultipartBody.Part postStringParams(String key, String value) {
        return MultipartBody.Part.createFormData(key, value);
    }
    public static MultipartBody.Part postFileParams(String key, File file) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData(key, file.getName(), fileBody);
    }
    public static MultipartBody.Part postFileParams(String mediaType, String key, File file) {
        RequestBody fileBody = RequestBody.create(MediaType.parse(mediaType), file);
        return MultipartBody.Part.createFormData(key, file.getName(), fileBody);
    }
}