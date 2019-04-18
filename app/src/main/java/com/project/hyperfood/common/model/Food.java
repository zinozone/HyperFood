package com.project.hyperfood.common.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable {

    private String id;
    private String carbohydrate;
    private String container;
    private String fat;
    private String foodName;
    private String grams;
    private String protein;
    private String serving;
    private String soduim;
    private String sugars;
    private String foodType;
    private int dayTime;

    public Food() {
    }

    protected Food(Parcel in) {
        id = in.readString();
        carbohydrate = in.readString();
        container = in.readString();
        fat = in.readString();
        foodName = in.readString();
        grams = in.readString();
        protein = in.readString();
        serving = in.readString();
        soduim = in.readString();
        sugars = in.readString();
        foodType = in.readString();
        dayTime = in.readInt();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(String carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getGrams() {
        return grams;
    }

    public void setGrams(String grams) {
        this.grams = grams;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public String getSoduim() {
        return soduim;
    }

    public void setSoduim(String soduim) {
        this.soduim = soduim;
    }

    public String getSugars() {
        return sugars;
    }

    public void setSugars(String sugars) {
        this.sugars = sugars;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public int getDayTime() {
        return dayTime;
    }

    public void setDayTime(int dayTime) {
        this.dayTime = dayTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(carbohydrate);
        dest.writeString(container);
        dest.writeString(fat);
        dest.writeString(foodName);
        dest.writeString(grams);
        dest.writeString(protein);
        dest.writeString(serving);
        dest.writeString(soduim);
        dest.writeString(sugars);
        dest.writeString(foodType);
        dest.writeInt(dayTime);
    }
}
