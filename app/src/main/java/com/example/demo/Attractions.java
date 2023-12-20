package com.example.demo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Attractions implements Parcelable
{
    private String pictureUrl;

    private String title;

    private String contents;

    private String comment;

    public Attractions() {
        super();
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

    public String getComment()
    {
        return this.comment;
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
        dest.writeString(comment);
    }

    public static final Creator<?> CREATOR = new Creator<Attractions>() {
        @Override
        public Attractions createFromParcel(Parcel source) {

            Attractions attractions = new Attractions();

            attractions.pictureUrl = source.readString();
            attractions.title = source.readString();
            attractions.contents = source.readString();
            attractions.comment = source.readString();

            return attractions;
        }
        @Override
        public Attractions[] newArray(int size) {
            return new Attractions[size];
        }
    };
}
