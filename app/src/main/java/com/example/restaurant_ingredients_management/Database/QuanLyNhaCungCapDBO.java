package com.example.restaurant_ingredients_management.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurant_ingredients_management.Model.Supplier;

import java.util.ArrayList;
import java.util.List;

public class QuanLyNhaCungCapDBO {
    private SQLiteDatabase db;
    private CreateDatabase dbHelper;

    public QuanLyNhaCungCapDBO(Context context) {
        dbHelper = new CreateDatabase(context); // Khởi tạo dbHelper
    }

    // Mở kết nối cơ sở dữ liệu
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    // Đóng kết nối cơ sở dữ liệu
    public void close() {
        dbHelper.close();
    }

    // Chèn một nhà cung cấp mới
    public long insertSupplier(Supplier supplier) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_SUPPLIER_NAME, supplier.getName());
        values.put(CreateDatabase.COLUMN_SUPPLIER_CONTACT_INFO, supplier.getContactInfo());
        values.put(CreateDatabase.COLUMN_SUPPLIER_ADDRESS, supplier.getAddress());
        return db.insert(CreateDatabase.TABLE_SUPPLIERS, null, values);
    }

    // Lấy tất cả các nhà cung cấp
    public ArrayList<Supplier> getAllSuppliers() {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        Cursor cursor = db.query(CreateDatabase.TABLE_SUPPLIERS, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Supplier supplier = new Supplier();
                supplier.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_ID)));
                supplier.setName(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_NAME)));
                supplier.setContactInfo(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_CONTACT_INFO)));
                supplier.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_ADDRESS)));
                suppliers.add(supplier);
            }
            cursor.close();
        }
        return suppliers;
    }

    // Lấy một nhà cung cấp theo id
    public Supplier getSupplierById(int id) {
        Supplier supplier = null;
        String selection = CreateDatabase.COLUMN_SUPPLIER_ID + "= ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = db.query(CreateDatabase.TABLE_SUPPLIERS, null, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            supplier = new Supplier();
            supplier.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_ID)));
            supplier.setName(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_NAME)));
            supplier.setContactInfo(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_CONTACT_INFO)));
            supplier.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_ADDRESS)));
            cursor.close();
        }
        return supplier;
    }

    // Cập nhật thông tin nhà cung cấp
    public int updateSupplier(Supplier supplier) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_SUPPLIER_NAME, supplier.getName());
        values.put(CreateDatabase.COLUMN_SUPPLIER_CONTACT_INFO, supplier.getContactInfo());
        values.put(CreateDatabase.COLUMN_SUPPLIER_ADDRESS, supplier.getAddress());

        String whereClause = CreateDatabase.COLUMN_SUPPLIER_ID + " = ?";
        String[] whereArgs = { String.valueOf(supplier.getId()) };
        return db.update(CreateDatabase.TABLE_SUPPLIERS, values, whereClause, whereArgs);
    }
    // Tìm kiếm nhà cung cấp theo tên
    public ArrayList<Supplier> searchSupplierByName(String name) {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        String selection = CreateDatabase.COLUMN_SUPPLIER_NAME + " LIKE ?";
        String[] selectionArgs = { "%" + name + "%" }; // Tìm kiếm tên chứa từ khóa
        Cursor cursor = db.query(
                CreateDatabase.TABLE_SUPPLIERS,
                null, // Lấy tất cả các cột
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Supplier supplier = new Supplier();
                supplier.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_ID)));
                supplier.setName(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_NAME)));
                supplier.setContactInfo(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_CONTACT_INFO)));
                supplier.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_ADDRESS)));
                suppliers.add(supplier);
            }
            cursor.close();
        }
        return suppliers;
    }
    // Xóa một nhà cung cấp
    public int deleteSupplier(int id) {
        String whereClause = CreateDatabase.COLUMN_SUPPLIER_ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };
        return db.delete(CreateDatabase.TABLE_SUPPLIERS, whereClause, whereArgs);
    }


}
