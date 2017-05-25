package com.am.demo.wisielec;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by malbor806 on 26.03.2017.
 */

public class Letter implements Parcelable {
    private String name;
    private int state;

    public Letter(String name, int state) {
        this.name = name;
        this.state = state;
    }

    protected Letter(Parcel in) {
        name = in.readString();
        state = in.readInt();
    }

    public void setState(int i) {
        state = i;
    }

    public int getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public static final Creator<Letter> CREATOR = new Creator<Letter>() {
        @Override
        public Letter createFromParcel(Parcel in) {
            return new Letter(in);
        }

        @Override
        public Letter[] newArray(int size) {
            return new Letter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(state);
    }
}
