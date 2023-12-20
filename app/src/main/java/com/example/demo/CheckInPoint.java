package com.example.demo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CheckInPoint implements Parcelable
{
    private String pictureUrl;
    private String title;
    private String contents;
    private String address;

    public CheckInPoint(String pictureUrl, String title, String contents, String address)
    {
        this.pictureUrl = pictureUrl;  this.title = title;
        this.contents = contents;  this.address = address;
    }

    public String getPictureUrl()
    {
        return this.pictureUrl;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getContent()
    {
        return this.contents;
    }

    public String getAddress()
    {
        return this.address;
    }

    public CheckInPoint()
    {
        super();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

        dest.writeString(pictureUrl);
        dest.writeString(title);
        dest.writeString(contents);
        dest.writeString(address);
    }

    public static final Creator<?> CREATOR = new Creator<CheckInPoint>() {
        @Override
        public CheckInPoint createFromParcel(Parcel source) {

            CheckInPoint checkInPoint = new CheckInPoint();

            checkInPoint.pictureUrl = source.readString();
            checkInPoint.title = source.readString();
            checkInPoint.contents = source.readString();
            checkInPoint.address = source.readString();

            return checkInPoint;
        }
        @Override
        public CheckInPoint[] newArray(int size) {
            return new CheckInPoint[size];
        }
    };
}
