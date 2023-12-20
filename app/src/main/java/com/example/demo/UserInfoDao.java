package com.example.demo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserInfoDao {

    @Query("select * from userinfo")
    List<UserInfo> getUserInfo();

    @Update
    void updateUserInfo(UserInfo userInfo);

    @Insert
    void insertUserInfo(UserInfo userInfo);
}
