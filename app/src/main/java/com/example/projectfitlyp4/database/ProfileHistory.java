package com.example.projectfitlyp4.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class ProfileHistory implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "fromName")
    private String fromName;
    @ColumnInfo(name = "toName")
    private String toName;
    @ColumnInfo(name = "fromEmail")
    private String fromEmail;
    @ColumnInfo(name = "toEmail")
    private String toEmail;
    @ColumnInfo(name = "fromGender")
    private String fromGender;
    @ColumnInfo(name = "toGender")
    private String toGender;
    @ColumnInfo(name = "date")
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getFromGender() {
        return fromGender;
    }

    public void setFromGender(String fromGender) {
        this.fromGender = fromGender;
    }

    public String getToGender() {
        return toGender;
    }

    public void setToGender(String toGender) {
        this.toGender = toGender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.fromName);
        dest.writeString(this.toName);
        dest.writeString(this.fromEmail);
        dest.writeString(this.toEmail);
        dest.writeString(this.fromGender);
        dest.writeString(this.toGender);
        dest.writeString(this.date);
    }

    @Ignore
    public ProfileHistory() {
    }

    public ProfileHistory(String fromName ,String toName ,String fromEmail ,String toEmail ,String fromGender ,String toGender, String date) {
        this.fromName = fromName;
        this.toName = toName;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.fromGender = fromGender;
        this.toGender = toGender;
        this.date = date;
    }

    private ProfileHistory(Parcel in) {
        this.id = in.readInt();
        this.fromName = in.readString();
        this.toName = in.readString();
        this.fromEmail = in.readString();
        this.toEmail = in.readString();
        this.fromGender = in.readString();
        this.toGender = in.readString();
        this.date = in.readString();
    }

    public static final Creator<ProfileHistory> CREATOR = new
            Creator<ProfileHistory>() {
                @Override
                public ProfileHistory createFromParcel(Parcel source) {
                    return new ProfileHistory(source);
                }

                @Override

                public ProfileHistory[] newArray(int size) {
                    return new ProfileHistory[size];
                }
            };
}
