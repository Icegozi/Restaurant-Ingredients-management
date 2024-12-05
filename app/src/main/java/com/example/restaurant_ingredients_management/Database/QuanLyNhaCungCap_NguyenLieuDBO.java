package com.example.restaurant_ingredients_management.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.restaurant_ingredients_management.Model.Supplier;
import com.example.restaurant_ingredients_management.Model.IngredientSupplier;

import java.util.ArrayList;
import java.util.List;

public class QuanLyNhaCungCap_NguyenLieuDBO {
    private SQLiteDatabase db;
    private CreateDatabase dbHelper;

    // Constructor
    public QuanLyNhaCungCap_NguyenLieuDBO(Context context) {
        dbHelper = new CreateDatabase(context);
    }

    // Mở kết nối
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    // Đóng kết nối
    public void close() {
        if (db != null && db.isOpen()) db.close();
        if (dbHelper != null) dbHelper.close();
    }

    // Lấy giá cả của nguyên liệu theo ID nhà cung cấp và ID nguyên liệu
    public double getPriceBySupplierAndIngredient(int supplierId, int ingredientId) {
        double price = -1; // Giá mặc định nếu không tìm thấy

        // Câu lệnh SQL để truy vấn giá từ bảng ingredient_suppliers
        String query = "SELECT " + CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_PRICE_PER_UNIT +
                " FROM " + CreateDatabase.TABLE_INGREDIENT_SUPPLIERS +
                " WHERE " + CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_SUPPLIER_ID + " = ? AND " +
                CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_INGREDIENT_ID + " = ?";

        // Thực hiện truy vấn
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(supplierId), String.valueOf(ingredientId)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // Lấy giá trị pricePerUnit từ kết quả
                price = cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_PRICE_PER_UNIT));
            }
            cursor.close(); // Đóng Cursor để giải phóng tài nguyên
        }

        return price; // Trả về giá hoặc -1 nếu không tìm thấy
    }


}
