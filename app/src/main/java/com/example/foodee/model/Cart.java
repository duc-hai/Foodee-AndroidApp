package com.example.foodee.model;

public class Cart {
    private String foodName;
    private String imageFood;
    private int amount;
    private int price;

    public Cart(String foodName, String imageFood, int amount, int price) {
        this.foodName = foodName;
        this.imageFood = imageFood;
        this.amount = amount;
        this.price = price;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getImageFood() {
        return imageFood;
    }

    public void setImageFood(String imageFood) {
        this.imageFood = imageFood;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

