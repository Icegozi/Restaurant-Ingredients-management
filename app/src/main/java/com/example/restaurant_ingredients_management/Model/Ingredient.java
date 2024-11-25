package com.example.restaurant_ingredients_management.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Ingredient {
    private int id;               // ID của nguyên liệu (Primary Key)
    private String name;          // Tên nguyên liệu
    private byte[] imageData;     // Ảnh của nguyên liệu
    private double quantity;      // Số lượng hiện tại
    private String unit;          // Đơn vị đo lường (vd: kg, gram, lít)
    private long expirationDate;  // Hạn sử dụng
    private boolean isLowStock;   // Trạng thái nếu số lượng thấp
    private long lastUpdated;     // Ngày cập nhật cuối cùng

    public Ingredient( String name, byte[] imageData, double quantity, String unit, long expirationDate, boolean isLowStock, long lastUpdated) {
        this.name = name;
        this.imageData = imageData;
        this.quantity = quantity;
        this.unit = unit;
        this.expirationDate = expirationDate;
        this.isLowStock = isLowStock;
        this.lastUpdated = lastUpdated;
    }

    public Ingredient(){

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

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
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

    public long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isLowStock() {
        return isLowStock;
    }

    public void setLowStock(boolean lowStock) {
        isLowStock = lowStock;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String convertLongToDate(long expirationDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        // Chuyển đổi từ long sang Date
        Date date = new Date(expirationDate);
        return sdf.format(date);
    }
    @Override
    public String toString() {
        return "Tên nguyên liệu: " + name + "\n"+ "Số lượng: " + quantity +" "+ unit +"\n"+"Hạn sử dụng: "+convertLongToDate(expirationDate);

    }
}
