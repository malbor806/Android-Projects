package com.am.demo.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

/**
 * Created by malbor806 on 09.04.2017.
 */

public class Item implements Parcelable {
    private int id;
    private String title;
    private double rate;
    private String description;
    @DrawableRes
    private int imageResId;

    public Item(int id, String title, double rate, int imageResId, String description) {
        this.id = id;
        this.title = title;
        this.rate = rate;
        this.imageResId = imageResId;
        this.description = description;
    }

    protected Item(Parcel in) {
        id = in.readInt();
        title = in.readString();
        rate = in.readDouble();
        imageResId = in.readInt();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeDouble(rate);
        dest.writeInt(imageResId);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public double getRate() {
        return rate;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getId() {
        return id;
    }

    public String getDescription(){
        return description;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
