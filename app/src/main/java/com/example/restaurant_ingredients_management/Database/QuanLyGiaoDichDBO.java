package com.example.restaurant_ingredients_management.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurant_ingredients_management.Model.IngredientSupplier;

import java.util.ArrayList;
import java.util.List;

public class QuanLyGiaoDichDBO {
    private SQLiteDatabase db;
    private CreateDatabase dbHelper;

    // Mở kết nối cơ sở dữ liệu
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    // Đóng kết nối cơ sở dữ liệu
    public void close() {
        dbHelper.close();
    }

    // Chèn một giao dịch nhà cung cấp nguyên liệu mới
    public long insertIngredientSupplier(IngredientSupplier ingredientSupplier) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_INGREDIENT_ID, ingredientSupplier.getIngredientId());
        values.put(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_SUPPLIER_ID, ingredientSupplier.getSupplierId());
        values.put(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_PRICE_PER_UNIT, ingredientSupplier.getPricePerUnit());
        values.put(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_SUPPLY_DATE, ingredientSupplier.getSupplyDate());
        return db.insert(CreateDatabase.TABLE_INGREDIENT_SUPPLIERS, null, values);
    }

    // Lấy tất cả các giao dịch nhà cung cấp nguyên liệu
    public List<IngredientSupplier> getAllIngredientSuppliers() {
        List<IngredientSupplier> ingredientSuppliers = new ArrayList<>();
        Cursor cursor = db.query(CreateDatabase.TABLE_INGREDIENT_SUPPLIERS, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                IngredientSupplier ingredientSupplier = new IngredientSupplier();
                ingredientSupplier.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_ID)));
                ingredientSupplier.setIngredientId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_INGREDIENT_ID)));
                ingredientSupplier.setSupplierId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_SUPPLIER_ID)));
                ingredientSupplier.setPricePerUnit(cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_PRICE_PER_UNIT)));
                ingredientSupplier.setSupplyDate(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_SUPPLY_DATE)));
                ingredientSuppliers.add(ingredientSupplier);
            }
            cursor.close();
        }
        return ingredientSuppliers;
    }

    // Lấy giao dịch nhà cung cấp nguyên liệu theo id
    public IngredientSupplier getIngredientSupplierById(int id) {
        IngredientSupplier ingredientSupplier = null;
        String selection = CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_ID + "= ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = db.query(CreateDatabase.TABLE_INGREDIENT_SUPPLIERS, null, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            ingredientSupplier = new IngredientSupplier();
            ingredientSupplier.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_ID)));
            ingredientSupplier.setIngredientId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_INGREDIENT_ID)));
            ingredientSupplier.setSupplierId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_SUPPLIER_ID)));
            ingredientSupplier.setPricePerUnit(cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_PRICE_PER_UNIT)));
            ingredientSupplier.setSupplyDate(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_SUPPLY_DATE)));
            cursor.close();
        }
        return ingredientSupplier;
    }

    // Cập nhật giao dịch nhà cung cấp nguyên liệu
    public int updateIngredientSupplier(IngredientSupplier ingredientSupplier) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_INGREDIENT_ID, ingredientSupplier.getIngredientId());
        values.put(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_SUPPLIER_ID, ingredientSupplier.getSupplierId());
        values.put(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_PRICE_PER_UNIT, ingredientSupplier.getPricePerUnit());
        values.put(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_SUPPLY_DATE, ingredientSupplier.getSupplyDate());

        String whereClause = CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_ID + " = ?";
        String[] whereArgs = { String.valueOf(ingredientSupplier.getId()) };
        return db.update(CreateDatabase.TABLE_INGREDIENT_SUPPLIERS, values, whereClause, whereArgs);
    }

    // Xóa giao dịch nhà cung cấp nguyên liệu
    public int deleteIngredientSupplier(int id) {
        String whereClause = CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };
        return db.delete(CreateDatabase.TABLE_INGREDIENT_SUPPLIERS, whereClause, whereArgs);
    }
}
