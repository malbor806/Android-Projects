package com.am.demo.tictactoe;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by malbor806 on 26.03.2017.
 */

public class Move implements Parcelable {
    private String token;
    private int isEnabled;

    public Move(String token, int isEnabled){
        this.token = token;
        this.isEnabled = isEnabled;
    }

    protected Move(Parcel in) {
        token = in.readString();
        isEnabled = in.readInt();
    }

    public String getToken(){
        return token;
    }

    public int getIsEnabled(){
        return  isEnabled;
    }

    public void setToken(String newToken){
        token = newToken;
    }

    public void setIsEnabled(int i){
        isEnabled = i;
    }

    public static final Creator<Move> CREATOR = new Creator<Move>() {
        @Override
        public Move createFromParcel(Parcel in) {
            return new Move(in);
        }

        @Override
        public Move[] newArray(int size) {
            return new Move[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeInt(isEnabled);
    }
}
