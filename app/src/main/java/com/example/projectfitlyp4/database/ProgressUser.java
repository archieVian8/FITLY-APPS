package com.example.projectfitlyp4.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "progress_user")
public class ProgressUser {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "weight")
    private double weight;

    @ColumnInfo(name = "height")
    private double height;

    @ColumnInfo(name = "bmi")
    private double bmi;

    @ColumnInfo(name = "firebase_id")
    private String firebaseId;

    public ProgressUser() {
        // No-arg constructor for Room
    }

    @Ignore
    public ProgressUser(String date, double weight, double height, double bmi) {
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
    }

    @Ignore
    public ProgressUser(String date, double weight, double height, double bmi, String firebaseId) {
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.firebaseId = firebaseId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }
}
