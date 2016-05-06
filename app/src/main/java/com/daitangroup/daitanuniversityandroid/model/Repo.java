package com.daitangroup.daitanuniversityandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("unused")
public class Repo implements Parcelable {

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //<editor-fold desc="### Parcelable stuff">

    public static final Creator<Repo> CREATOR = new Creator<Repo>() {
        public Repo createFromParcel(Parcel in) {
            return new Repo(in);
        }

        public Repo[] newArray(int size) {
            return new Repo[size];
        }
    };

    private Repo(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
    }

    //</editor-fold..>
}