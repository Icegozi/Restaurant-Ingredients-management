package com.example.restaurant_ingredients_management.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurant_ingredients_management.Model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class QuanLyNguyenLieuDBO {
    private SQLiteDatabase db;
    private CreateDatabase dbHelper;

    //mở kết nối cơ sở dữ liệu
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    //đóng kết nối cơ sở dữ liệu
    public void close(){
        dbHelper.close();
    }

    //chèn một nguyên liệu mới
    public long insertIngreddient(Ingredient ingredient){
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_INGREDIENT_NAME, ingredient.getName());
        values.put(CreateDatabase.COLUMN_INGREDIENT_ID, ingredient.getId());
        values.put(CreateDatabase.COLUMN_INGREDIENT_IMAGE,ingredient.getImageData());
        values.put(CreateDatabase.COLUMN_INGREDIENT_EXPIRATION_DATE,ingredient.getExpirationDate());
        values.put(CreateDatabase.COLUMN_INGREDIENT_IS_LOW_STOCK,ingredient.isLowStock());
        values.put(CreateDatabase.COLUMN_INGREDIENT_QUANTITY,ingredient.getQuantity());
        values.put(CreateDatabase.COLUMN_INGREDIENT_UNIT,ingredient.getUnit());
        values.put(CreateDatabase.COLUMN_INGREDIENT_LAST_UPDATED,ingredient.getLastUpdated());
        return db.insert(CreateDatabase.TABLE_INGREDIENTS,null,values);
    }

    //Lấy tất cả các nguyên liệu
    public List<Ingredient> getAllIngredients(){
        List<Ingredient> ingredients = new ArrayList<>();
        Cursor cursor = db.query(CreateDatabase.TABLE_INGREDIENTS,null,null,null,null,null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                Ingredient ingredient = new Ingredient();
                ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_ID)));
                ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_NAME)));
                ingredient.setImageData(cursor.getBlob(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_IMAGE)));
                ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_QUANTITY)));
                ingredient.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_UNIT)));
                ingredient.setExpirationDate((cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_EXPIRATION_DATE))));
                ingredient.setLowStock(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_IS_LOW_STOCK))==1);
                ingredient.setLastUpdated(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_LAST_UPDATED)));
                ingredients.add(ingredient);
            }
            cursor.close();
        }
        return ingredients;
    }

    //lấy một nguyên liệu theo id
    public Ingredient getIngredientById(int id){
        Ingredient ingredient = null;
        String selection = CreateDatabase.COLUMN_INGREDIENT_ID + "= ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = db.query(CreateDatabase.TABLE_INGREDIENTS, null, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()){
            ingredient = new Ingredient();
            ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_ID)));
            ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_NAME)));
            ingredient.setImageData(cursor.getBlob(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_IMAGE)));
            ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_QUANTITY)));
            ingredient.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_UNIT)));
            ingredient.setExpirationDate((cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_EXPIRATION_DATE))));
            ingredient.setLowStock(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_IS_LOW_STOCK))==1);
            ingredient.setLastUpdated(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_LAST_UPDATED)));
            cursor.close();
        }
        return ingredient;
    }

    //cập nhật một nguyên liệu
    public int updateIngredient(Ingredient ingredient){
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_INGREDIENT_NAME, ingredient.getName());
        values.put(CreateDatabase.COLUMN_INGREDIENT_IMAGE,ingredient.getImageData());
        values.put(CreateDatabase.COLUMN_INGREDIENT_EXPIRATION_DATE,ingredient.getExpirationDate());
        values.put(CreateDatabase.COLUMN_INGREDIENT_IS_LOW_STOCK,ingredient.isLowStock() ? 1 : 0);
        values.put(CreateDatabase.COLUMN_INGREDIENT_QUANTITY,ingredient.getQuantity());
        values.put(CreateDatabase.COLUMN_INGREDIENT_UNIT,ingredient.getUnit());
        values.put(CreateDatabase.COLUMN_INGREDIENT_LAST_UPDATED,ingredient.getLastUpdated());

        String whereClause = CreateDatabase.COLUMN_INGREDIENT_ID + " = ?";
        String[] whereArgs = { String.valueOf(ingredient.getId()) };
        return db.update(CreateDatabase.TABLE_INGREDIENTS, values, whereClause, whereArgs);
    }

    // Xóa một nguyên liệu
    public int deleteIngredient(int id) {
        String whereClause = CreateDatabase.COLUMN_INGREDIENT_ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };
        return db.delete(CreateDatabase.TABLE_INGREDIENTS, whereClause, whereArgs);
    }
}
