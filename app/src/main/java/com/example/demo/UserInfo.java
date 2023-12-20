package com.example.demo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "userinfo")
public class UserInfo implements Parcelable {

    public UserInfo(int id, String url, String nickname,
    String password, int level)
    {
        this.id = id;
        this.url = url;
        this.nickname = nickname;
        this.password = password;
        this.level = level;
    }

    public UserInfo(){}

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    public int id;

    @ColumnInfo(name = "url")
    @SerializedName("url")
    public String url;

    @ColumnInfo(name = "nickname")
    @SerializedName("nickname")
    public String nickname;

    @ColumnInfo(name = "password")
    @SerializedName("password")
    public String password;

    @ColumnInfo(name = "level")
    @SerializedName("level")
    public int level;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) { // 把需要序列化的字段写进去
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeString(nickname);
        dest.writeString(password);
        dest.writeInt(level);
    }

    public static final Creator<?> CREATOR=new Creator<UserInfo>() {
        // 序列化和反序列化的顺序要一致
        @Override
        public UserInfo createFromParcel(Parcel source) {

            UserInfo userInfo = new UserInfo();

            userInfo.id = source.readInt();
            userInfo.url = source.readString();
            userInfo.nickname = source.readString();
            userInfo.password = source.readString();
            userInfo.level = source.readInt();

            return userInfo;
        }
        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
