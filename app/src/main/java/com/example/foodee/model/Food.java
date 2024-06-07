package com.example.foodee.model;

import java.io.Serializable;

public class Food implements Serializable{
    private String image;
    private String foodName;
    private int price;
    private String infor;
    private String details;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getInfor() {
        return infor;
    }

    public void setInfor(String infor) {
        this.infor = infor;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
