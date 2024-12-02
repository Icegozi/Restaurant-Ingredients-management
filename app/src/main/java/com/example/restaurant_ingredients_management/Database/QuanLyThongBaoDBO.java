package com.example.restaurant_ingredients_management.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurant_ingredients_management.Model.StockAlert;

import java.util.ArrayList;
import java.util.List;

public class QuanLyThongBaoDBO {
    private SQLiteDatabase db;
    private CreateDatabase dbHelper;

    public QuanLyThongBaoDBO(Context context) {
        dbHelper = new CreateDatabase(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
        dbHelper.close();
    }

    // Phương thức để lưu cảnh báo vào cơ sở dữ liệu
    public long insertStockAlert(StockAlert alert) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_STOCK_ALERT_INGREDIENT_ID, alert.getIngredientId());
        values.put(CreateDatabase.COLUMN_STOCK_ALERT_TYPE, alert.getAlertType());
        values.put(CreateDatabase.COLUMN_STOCK_ALERT_DATE, alert.getAlertDate());
        values.put(CreateDatabase.COLUMN_STOCK_ALERT_IS_RESOLVED, alert.isResolved() ? 1 : 0);
        return db.insert(CreateDatabase.TABLE_STOCK_ALERTS, null, values);
    }

    // Phương thức để lấy tất cả cảnh báo từ cơ sở dữ liệu
    public List<StockAlert> getAllStockAlerts() {
        List<StockAlert> alertList = new ArrayList<>();
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + CreateDatabase.TABLE_STOCK_ALERTS;
            cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    StockAlert alert = new StockAlert();
                    alert.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_STOCK_ALERT_ID)));
                    alert.setIngredientId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_STOCK_ALERT_INGREDIENT_ID)));
                    alert.setAlertType(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_STOCK_ALERT_TYPE)));
                    alert.setAlertDate(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_STOCK_ALERT_DATE)));
                    alert.setResolved(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_STOCK_ALERT_IS_RESOLVED)) == 1);

                    alertList.add(alert);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return alertList;
    }

    // Phương thức để lấy cảnh báo chưa xử lý
    public List<StockAlert> getUnresolvedAlerts() {
        List<StockAlert> alertList = new ArrayList<>();
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + CreateDatabase.TABLE_STOCK_ALERTS +
                    " WHERE " + CreateDatabase.COLUMN_STOCK_ALERT_IS_RESOLVED + " = 0";
            cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    StockAlert alert = new StockAlert();
                    alert.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_STOCK_ALERT_ID)));
                    alert.setIngredientId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_STOCK_ALERT_INGREDIENT_ID)));
                    alert.setAlertType(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_STOCK_ALERT_TYPE)));
                    alert.setAlertDate(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_STOCK_ALERT_DATE)));
                    alert.setResolved(false);

                    alertList.add(alert);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return alertList;
    }

    // Phương thức để cập nhật trạng thái đã xử lý
    public int resolveAlert(int alertId) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_STOCK_ALERT_IS_RESOLVED, 1);
        return db.update(CreateDatabase.TABLE_STOCK_ALERTS, values,
                CreateDatabase.COLUMN_STOCK_ALERT_ID + " = ?",
                new String[]{String.valueOf(alertId)});
    }

    // Phương thức để xóa tất cả cảnh báo
    public int deleteAllAlerts() {
        return db.delete(CreateDatabase.TABLE_STOCK_ALERTS, null, null);
    }
}
