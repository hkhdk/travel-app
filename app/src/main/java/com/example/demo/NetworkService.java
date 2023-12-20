package com.example.demo;

import android.util.Log;

import androidx.annotation.NonNull;


import java.io.File;
import java.net.HttpURLConnection;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public class NetworkService {
    private final String TAG = "NetWork request";
    private UserInfo userInfo;
    private int currentPageOfAttractions = 0;
    private int currentPageOfCheckInPoints = 0;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")  // server url
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    private final RequestInterface request = retrofit.create(RequestInterface.class);

    public interface NetworkCallBack{
        void callBackOf();
        void dataChangedOfAttractions(List<Attractions> list);
        void dataChangedOfCheckInPoints(List<CheckInPoint> list);
    }

    public interface UserLoginSuccessOrFail{
        void userLoginSuccess(UserInfo userInfo);
        void userLoginFail();
    }

    public interface PostCheckInPointsResult{
        void postSuccess();
        void postFail();
    }

    // all request methods
    public interface RequestInterface{
        @POST("/userInfo")
        Call<UserInfo> postUserInfo(@Body UserInfo userInfo);

        @GET("/userInfo/")
        Call<UserInfo> getUserInfo();

        @GET("/attractions/list")
        Call<List<Attractions>> getAttractionsList(@Query("page") int page);

        @GET("/checkInPoint/list")
        Call<List<CheckInPoint>> getCheckInPointList(@Query("page") int page);

        @Multipart
        @POST("/checkInPoint")
        Call<CheckInPoint> postCheckInPoint(@Part MultipartBody.Part part,  @Body CheckInPoint checkInPoint);

        @GET("/user/login")
        Call<UserInfo> userLogin(@Query("userName") String userName, @Query("passWord") String passWord);
    }

    public UserInfo getUserInfo()
    {
        Call<UserInfo> getcall = request.getUserInfo();
        getcall.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    userInfo = response.body();
                    Log.i(TAG, "get userInfo success");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Log.i(TAG, "get userInfo fail");
            }
        });

        return userInfo;
    }

    public void postUserInfo(UserInfo userInfo)
    {
        Call<UserInfo> postcall = request.postUserInfo(userInfo);
        postcall.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Log.i(TAG, "post userInfo success");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Log.i(TAG, "post userInfo fail");
            }
        });
    }

    public void getAttractionsList(NetworkCallBack cb)
    {
        Call<List<Attractions>> call = request.getAttractionsList(currentPageOfAttractions++);
        call.enqueue(new Callback<List<Attractions>>() {
            @Override
            public void onResponse(@NonNull Call<List<Attractions>> call, @NonNull Response<List<Attractions>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {  // 推荐的一批景点列表

                    if(response.body() != null){
                        if(cb != null) {
                            cb.dataChangedOfAttractions(response.body());
                        }
                    }
                    if(cb != null){
                        cb.callBackOf();
                    }
                    Log.i(TAG, "get attractionsList success");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Attractions>> call, @NonNull Throwable t) {
                if(cb != null){
                    cb.callBackOf();
                }
                Log.i(TAG, "get attractionsList fail");
            }
        });
    }

    public void getCheckInPointList(NetworkCallBack cb)
    {
        Call<List<CheckInPoint>> call = request.getCheckInPointList(currentPageOfCheckInPoints++);
        call.enqueue(new Callback<List<CheckInPoint>>() {
            @Override
            public void onResponse(@NonNull Call<List<CheckInPoint>> call, @NonNull Response<List<CheckInPoint>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {

                    if(response.body() != null){
                        if(cb != null) {
                            cb.dataChangedOfCheckInPoints(response.body());
                        }
                    }
                    if(cb != null){
                        cb.callBackOf();
                    }
                    Log.i(TAG, "get checkInPoint success");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CheckInPoint>> call, @NonNull Throwable t) {
                if(cb != null){
                    cb.callBackOf();
                }
                Log.i(TAG, "get checkInPoint fail");
            }
        });
    }

    private MultipartBody.Part createMultipartBodyPart(String picturePath) {
        File file = new File(picturePath);
        RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("file", file.getName(), body);
    }

    public void postCheckInPoint(String picturePath, CheckInPoint checkInPoint, PostCheckInPointsResult cb)
    {
        MultipartBody.Part part = createMultipartBodyPart(picturePath); // 把图片当成 part 上传
        Call<CheckInPoint> call = request.postCheckInPoint(part, checkInPoint);
        call.enqueue(new Callback<CheckInPoint>() {
            @Override
            public void onResponse(@NonNull Call<CheckInPoint> call, @NonNull Response<CheckInPoint> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    cb.postSuccess();
                    Log.i(TAG, "post checkInPoint success");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CheckInPoint> call, @NonNull Throwable t) {
                cb.postFail();
                Log.i(TAG, "post checkInPoint fail");
            }
        });
    }

    public void userLogin(String userName, String passWord, UserLoginSuccessOrFail cb)
    {
        Call<UserInfo> call = request.userLogin(userName, passWord);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if(response.code() == HttpURLConnection.HTTP_OK)
                {
                    cb.userLoginSuccess(response.body());
                    Log.i(TAG, "user login success");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                cb.userLoginFail();
                Log.i(TAG, "user login fail");
            }
        });
    }
}
