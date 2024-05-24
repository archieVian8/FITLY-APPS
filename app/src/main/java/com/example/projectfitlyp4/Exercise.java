package com.example.projectfitlyp4;

public class Exercise {
    private int imageResId;
    private String name;
    private String calorieCount;

    public Exercise(int imageResId, String name, String calorieCount) {
        this.imageResId = imageResId;
        this.name = name;
        this.calorieCount = calorieCount;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public String getCalorieCount() {
        return calorieCount;
    }
}
