package com.example.demo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = UserInfo.class, version = 2, exportSchema = false)
public abstract class WechatDataBase extends RoomDatabase {
    private static WechatDataBase INSTANCE;
    private static final Object sLock = new Object();
    public abstract UserInfoDao userInfoDao();


    public static WechatDataBase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(), WechatDataBase.class, "wechat.db")
                                .allowMainThreadQueries()
                                .build();
            }
            return INSTANCE;
        }
    }

}
