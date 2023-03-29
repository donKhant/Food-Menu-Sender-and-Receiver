package com.example.vote.Models;

/**
 * Created by Don, Android Developer, Date unknown
 * free to use for educational purposes
 *
 * contact me at donkhant1@gmail.com
 */


public class RemovableFoodModel {

    public static final String TABLE_NAME = "food";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_FIREBASE_ID = "firebase_id";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_URL + " TEXT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_INGREDIENTS + " TEXT,"
                    + COLUMN_PRICE + " TEXT,"
                    + COLUMN_FIREBASE_ID + " TEXT"
                    + ")";

    public String firebaseId, foodUrl, name, ingredients, price;
    public int id;

    public RemovableFoodModel() {}

    public RemovableFoodModel(int id, String url, String FoodName, String FoodIngredients, String FoodPrice, String firebaseId) {
        this.id = id;
        this.foodUrl = url;
        this.name = FoodName;
        this.ingredients = FoodIngredients;
        this.price = FoodPrice;
        this.firebaseId = firebaseId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
