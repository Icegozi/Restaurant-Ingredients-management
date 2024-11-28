package com.example.restaurant_ingredients_management.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.restaurant_ingredients_management.Model.Ingredient;
import com.example.restaurant_ingredients_management.Model.IngredientSupplier;
import com.example.restaurant_ingredients_management.Model.Supplier;

import java.util.ArrayList;
import java.util.List;

public class QuanLyNguyenLieuDBO {
    private SQLiteDatabase db;
    private CreateDatabase dbHelper;

    // Constructor để khởi tạo dbHelper
    public QuanLyNguyenLieuDBO(Context context) {
        dbHelper = new CreateDatabase(context); // Khởi tạo dbHelper với Context
    }

    // Mở kết nối cơ sở dữ liệu
    public void open() throws SQLException {
        if (dbHelper != null) {
            db = dbHelper.getWritableDatabase();
        } else {
            throw new SQLException("Database helper is null");
        }
    }

    // Đóng kết nối cơ sở dữ liệu
    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    // Thêm một nguyên liệu mới
    public long insertIngredient(Ingredient ingredient) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_INGREDIENT_NAME, ingredient.getName());
        values.put(CreateDatabase.COLUMN_INGREDIENT_IMAGE, ingredient.getImageData());
        values.put(CreateDatabase.COLUMN_INGREDIENT_EXPIRATION_DATE, ingredient.getExpirationDate());
        values.put(CreateDatabase.COLUMN_INGREDIENT_IS_LOW_STOCK, ingredient.isLowStock() ? 1 : 0);
        values.put(CreateDatabase.COLUMN_INGREDIENT_QUANTITY, ingredient.getQuantity());
        values.put(CreateDatabase.COLUMN_INGREDIENT_UNIT, ingredient.getUnit());
        values.put(CreateDatabase.COLUMN_INGREDIENT_LAST_UPDATED, ingredient.getLastUpdated());
        return db.insert(CreateDatabase.TABLE_INGREDIENTS, null, values); // Trả về ID dòng vừa thêm
    }

    // Lấy tất cả các nguyên liệu
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        Cursor cursor = db.query(CreateDatabase.TABLE_INGREDIENTS, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_ID)));
                ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_NAME)));
                ingredient.setImageData(cursor.getBlob(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_IMAGE)));
                ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_QUANTITY)));
                ingredient.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_UNIT)));
                ingredient.setExpirationDate(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_EXPIRATION_DATE)));
                ingredient.setLowStock(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_IS_LOW_STOCK)) == 1);
                ingredient.setLastUpdated(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_LAST_UPDATED)));
                ingredients.add(ingredient);
            }
            cursor.close();
        }
        return ingredients;
    }

    // Lấy một nguyên liệu theo id
    public Ingredient getIngredientById(int id) {
        Ingredient ingredient = null;
        String selection = CreateDatabase.COLUMN_INGREDIENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(CreateDatabase.TABLE_INGREDIENTS, null, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            ingredient = new Ingredient();
            ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_ID)));
            ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_NAME)));
            ingredient.setImageData(cursor.getBlob(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_IMAGE)));
            ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_QUANTITY)));
            ingredient.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_UNIT)));
            ingredient.setExpirationDate(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_EXPIRATION_DATE)));
            ingredient.setLowStock(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_IS_LOW_STOCK)) == 1);
            ingredient.setLastUpdated(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_LAST_UPDATED)));
            cursor.close();
        }
        return ingredient;
    }

    // Cập nhật một nguyên liệu
    public int updateIngredient(Ingredient ingredient) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_INGREDIENT_NAME, ingredient.getName());
        values.put(CreateDatabase.COLUMN_INGREDIENT_IMAGE, ingredient.getImageData());
        values.put(CreateDatabase.COLUMN_INGREDIENT_EXPIRATION_DATE, ingredient.getExpirationDate());
        values.put(CreateDatabase.COLUMN_INGREDIENT_IS_LOW_STOCK, ingredient.isLowStock() ? 1 : 0);
        values.put(CreateDatabase.COLUMN_INGREDIENT_QUANTITY, ingredient.getQuantity());
        values.put(CreateDatabase.COLUMN_INGREDIENT_UNIT, ingredient.getUnit());
        values.put(CreateDatabase.COLUMN_INGREDIENT_LAST_UPDATED, ingredient.getLastUpdated());

        String whereClause = CreateDatabase.COLUMN_INGREDIENT_ID + " = ?";
        String[] whereArgs = {String.valueOf(ingredient.getId())};
        return db.update(CreateDatabase.TABLE_INGREDIENTS, values, whereClause, whereArgs);
    }

    // Xóa một nguyên liệu
    public int deleteIngredient(int id) {
        String whereClause = CreateDatabase.COLUMN_INGREDIENT_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        return db.delete(CreateDatabase.TABLE_INGREDIENTS, whereClause, whereArgs);
    }

    //Thêm liên kết giá tiền giữa nhà cung cấp và nguyên liệu
    public long insertIngredientSupplier(IngredientSupplier ingredientSupplier) {
        ContentValues values = new ContentValues();
        values.put("ingredientId", ingredientSupplier.getIngredientId());  // Đảm bảo có giá trị
        values.put("supplierId", ingredientSupplier.getSupplierId());  // Đảm bảo có giá trị
        values.put("pricePerUnit", ingredientSupplier.getPricePerUnit());
        values.put("supplyDate", ingredientSupplier.getSupplyDate());

        Log.d("DEBUG", "Values to insert: " + values.toString());  // Log values trước khi chèn

        return db.insert(CreateDatabase.TABLE_INGREDIENT_SUPPLIERS, null, values);
    }



    // Lấy danh sách nhà cung cấp cho một nguyên liệu
    public List<IngredientSupplier> getSuppliersByIngredientId(int ingredientId) {
        List<IngredientSupplier> suppliers = new ArrayList<>();
        String selection = CreateDatabase.COLUMN_INGREDIENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(ingredientId)};
        Cursor cursor = db.query(CreateDatabase.TABLE_INGREDIENT_SUPPLIERS, null, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                IngredientSupplier supplier = new IngredientSupplier(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_ID)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_PRICE_PER_UNIT)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_SUPPLY_DATE))
                );
                suppliers.add(supplier);
            }
            cursor.close();
        }
        return suppliers;
    }

    // Lấy tất cả các nhà cung cấp
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query(CreateDatabase.TABLE_SUPPLIERS, null, null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Supplier supplier = new Supplier();
                    supplier.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_ID)));
                    supplier.setName(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_NAME)));
                    supplier.setContactInfo(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_CONTACT_INFO)));
                    supplier.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_ADDRESS)));
                    suppliers.add(supplier);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return suppliers;
    }


}