package com.example.demo;

import android.content.Context;

import java.util.List;

public class Service {
    private final UserInfoDao dao;

    public Service(Context context){
        WechatDataBase dataBase = WechatDataBase.getInstance(context);
        dao = dataBase.userInfoDao();
    }

    public List<UserInfo> getUserInfo(){
        return dao.getUserInfo();
    }

    public void insertUserInfo(UserInfo userInfo){
        dao.insertUserInfo(userInfo);
    }

    public void updateUserInfo(UserInfo userInfo){
       dao.updateUserInfo(userInfo);
    }
}
