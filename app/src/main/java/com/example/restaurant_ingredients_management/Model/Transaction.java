package com.example.restaurant_ingredients_management.Model;

import android.icu.text.SimpleDateFormat;

import java.util.Date;

public class Transaction {
    private int id;                    // ID giao dịch (Primary Key)
    private int supplierId;
    private int ingredientId;          // ID của nguyên liệu liên quan
    private long transactionDate;      // Ngày giao dịch
    private String transactionType;    // Loại giao dịch ("input" hoặc "output")
    private double quantity;           // Số lượng nhập/xuất
    private String unit;               // Đơn vị đo lường
    private String note;               // Ghi chú giao dịch



    public Transaction(int id,int supplierId, int ingredientId, long transactionDate, String transactionType, double quantity, String unit, String note) {
        this.id = id;
        this.supplierId = supplierId;
        this.ingredientId = ingredientId;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.unit = unit;
        this.note = note;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public Transaction() {
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

    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(timestamp);
        return sdf.format(date);
    }
    @Override
    public String toString() {
        return
                "Ngày giao dịch: " + formatDate(transactionDate) + "\n" +
                "Kiểu giao dịch: " + transactionType + '\n' +
                "Số lượng: " + quantity + "\n"+
                "Đơn vị: " + unit + '\n' +
                "Ghi chú: " + note;
    }

}
