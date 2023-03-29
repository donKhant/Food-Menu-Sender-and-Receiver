package com.example.vote.LocalDB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vote.Models.RemovableFoodModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Don, Date unknown
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Food_db";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(RemovableFoodModel.CREATE_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + RemovableFoodModel.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertFood(RemovableFoodModel food) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        //values.put(FoodModel.);
        values.put(RemovableFoodModel.COLUMN_URL, food.getFoodUrl());
        values.put(RemovableFoodModel.COLUMN_NAME, food.getName());
        values.put(RemovableFoodModel.COLUMN_INGREDIENTS, food.getIngredients());
        values.put(RemovableFoodModel.COLUMN_PRICE, food.getPrice());
        values.put(RemovableFoodModel.COLUMN_FIREBASE_ID, food.getFirebaseId());
        // to-do // values.put(FoodModel.COLUMN_CATEGORY_ID, food.getCategoryId());

        // insert row
        long id = db.insert(RemovableFoodModel.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<RemovableFoodModel> getFood() {
        List<RemovableFoodModel> foods = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + RemovableFoodModel.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                RemovableFoodModel f = new RemovableFoodModel();
                f.setId(cursor.getInt(cursor.getColumnIndexOrThrow(RemovableFoodModel.COLUMN_ID)));
                f.setFoodUrl(cursor.getString(cursor.getColumnIndexOrThrow(RemovableFoodModel.COLUMN_URL)));
                f.setName(cursor.getString(cursor.getColumnIndexOrThrow(RemovableFoodModel.COLUMN_NAME)));
                f.setIngredients(cursor.getString(cursor.getColumnIndexOrThrow(RemovableFoodModel.COLUMN_INGREDIENTS)));
                f.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(RemovableFoodModel.COLUMN_PRICE)));
                f.setFirebaseId(cursor.getString(cursor.getColumnIndexOrThrow(RemovableFoodModel.COLUMN_FIREBASE_ID)));

                foods.add(f);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return foods;
    }

    public int getFoodCount() {
        String countQuery = "SELECT  * FROM " + RemovableFoodModel.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public void deleteFood(RemovableFoodModel f) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RemovableFoodModel.TABLE_NAME, RemovableFoodModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(f.getId())});
        db.close();
    }

    public void deleteAllFood() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + RemovableFoodModel.TABLE_NAME);
        db.close();
    }

    public RemovableFoodModel getFoodById(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(RemovableFoodModel.TABLE_NAME,
                new String[]{RemovableFoodModel.COLUMN_ID, RemovableFoodModel.COLUMN_URL, RemovableFoodModel.COLUMN_NAME, RemovableFoodModel.COLUMN_INGREDIENTS, RemovableFoodModel.COLUMN_PRICE, RemovableFoodModel.COLUMN_FIREBASE_ID},
                RemovableFoodModel.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        RemovableFoodModel rf = new RemovableFoodModel(
                cursor.getInt(cursor.getColumnIndexOrThrow(RemovableFoodModel.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(RemovableFoodModel.COLUMN_URL)),
                cursor.getString(cursor.getColumnIndexOrThrow(RemovableFoodModel.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(RemovableFoodModel.COLUMN_INGREDIENTS)),
                cursor.getString(cursor.getColumnIndexOrThrow(RemovableFoodModel.COLUMN_PRICE)),
                cursor.getString(cursor.getColumnIndexOrThrow(RemovableFoodModel.COLUMN_FIREBASE_ID))
        );

        // close the db connection
        cursor.close();

        return rf;
    }

    public ArrayList<RemovableFoodModel> getFoodByCategory(String categoryId) {
        ArrayList<RemovableFoodModel> foods = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] args = {String.valueOf(categoryId)};

        Cursor cursor = db.query(RemovableFoodModel.TABLE_NAME,
                new String[]{RemovableFoodModel.COLUMN_ID, RemovableFoodModel.COLUMN_URL, RemovableFoodModel.COLUMN_NAME, RemovableFoodModel.COLUMN_INGREDIENTS, RemovableFoodModel.COLUMN_PRICE, RemovableFoodModel.COLUMN_FIREBASE_ID},
                 null,
                args, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                RemovableFoodModel f = new RemovableFoodModel();
                f.setName(cursor.getString(cursor.getColumnIndexOrThrow(RemovableFoodModel.COLUMN_NAME)));
                f.setIngredients(cursor.getString(cursor.getColumnIndexOrThrow(RemovableFoodModel.COLUMN_INGREDIENTS)));
                f.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(RemovableFoodModel.COLUMN_PRICE)));

                foods.add(f);
            } while (cursor.moveToNext());
        }

        db.close();
        return foods;

    }

}
