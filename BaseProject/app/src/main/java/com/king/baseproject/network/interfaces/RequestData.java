package com.king.baseproject.network.interfaces;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;
/**
 * Created by king on 2017/2/22.
 */
public interface RequestData {
//       @FormUrlEncoded
//        @POST("{url}")
//        Observable<Object> query(@Path("url") String url, @FieldMap Map<String, Object> params);
      //键值对传输参数
      @FormUrlEncoded
      @POST("{url1}/{url2}")
      Observable<Object> query(@Path("url1")String url1,@Path("url2") String url2,@FieldMap Map<String, Object> params);

      @Headers({"Content-Type: application/json","Accept: application/json"})
      @POST("register")
      Observable<Object> queryto(@Body RequestBody body );
}