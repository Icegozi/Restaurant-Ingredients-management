package com.example.restaurant_ingredients_management.Model;

import java.util.Date;

public class StockAlert {
    private int id;                   // ID cảnh báo (Primary Key)
    private int ingredientId;         // ID của nguyên liệu liên quan
    private String alertType;         // Loại cảnh báo ("low_stock" hoặc "near_expiration")
    private long alertDate;           // Ngày tạo cảnh báo
    private boolean isResolved;       // Trạng thái đã xử lý

    public StockAlert() {
    }

    public StockAlert(int ingredientId, String alertType, long alertDate, boolean isResolved) {
        this.ingredientId = ingredientId;
        this.alertType = alertType;
        this.alertDate = alertDate;
        this.isResolved = isResolved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public long getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(long alertDate) {
        this.alertDate = alertDate;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }
}
