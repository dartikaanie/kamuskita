package com.anie.dara.kamuskita;

import android.os.Parcel;
import android.os.Parcelable;

public class kamus implements Parcelable {
    private int id;
    private String keyword;
    private String arti;

    public kamus(String keyword, String arti) {
        this.keyword = keyword;
        this.arti =arti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.keyword);
        dest.writeString(this.arti);
    }

    public kamus() {
    }

    protected kamus(Parcel in) {
        this.id = in.readInt();
        this.keyword = in.readString();
        this.arti = in.readString();
    }

    public static final Parcelable.Creator<kamus> CREATOR = new Parcelable.Creator<kamus>() {
        @Override
        public kamus createFromParcel(Parcel source) {
            return new kamus(source);
        }

        @Override
        public kamus[] newArray(int size) {
            return new kamus[size];
        }
    };
}
