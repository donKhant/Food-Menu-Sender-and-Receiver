package com.example.vote.Models;

/**
 * Created by Don, Date unknown
 * free to use for educational purposes
 *
 * contact me at donkhant1@gmail.com
 */


public class FoodModel {

    public String foodUrl, name, ingredients, price, firebaseId;
    public int id;

    public FoodModel() {}

    public FoodModel(String url, String FoodName, String FoodIngredients, String FoodPrice, String firebaseId) {
        this.foodUrl = url;
        this.name = FoodName;
        this.ingredients = FoodIngredients;
        this.price = FoodPrice;
        this.firebaseId = firebaseId;
    }

    public String getFoodUrl() {
        return foodUrl;
    }
    public void setFoodUrl(String url) {
        this.foodUrl = url;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {this.price = price;}

    public String getFirebaseId() {
        return firebaseId;
    }
    public void setFirebaseId(String firebaseId) {this.firebaseId = firebaseId;}
}
