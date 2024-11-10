package com.example.restaurant_ingredients_management.Model;

import java.util.Date;

public class Ingredient {
    private int id;               // ID của nguyên liệu (Primary Key)
    private String name;          // Tên nguyên liệu
    private double quantity;      // Số lượng hiện tại
    private String unit;          // Đơn vị đo lường (vd: kg, gram, lít)
    private Date expirationDate;  // Hạn sử dụng
    private boolean isLowStock;   // Trạng thái nếu số lượng thấp
    private Date lastUpdated;     // Ngày cập nhật cuối cùng

    public Ingredient(int id, String name, double quantity, String unit, Date expirationDate, boolean isLowStock, Date lastUpdated) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expirationDate = expirationDate;
        this.isLowStock = isLowStock;
        this.lastUpdated = lastUpdated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isLowStock() {
        return isLowStock;
    }

    public void setLowStock(boolean lowStock) {
        isLowStock = lowStock;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
