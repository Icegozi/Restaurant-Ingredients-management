package com.example.restaurant_ingredients_management.Model;

import java.util.Date;

public class IngredientSupplier {
    private int id;              // ID liên kết (Primary Key)
    private int ingredientId;    // ID nguyên liệu
    private int supplierId;      // ID nhà cung cấp
    private double pricePerUnit; // Giá trên mỗi đơn vị
    private Date supplyDate;     // Ngày cung cấp gần nhất

    public IngredientSupplier(int id, int ingredientId, int supplierId, double pricePerUnit, Date supplyDate) {
        this.id = id;
        this.ingredientId = ingredientId;
        this.supplierId = supplierId;
        this.pricePerUnit = pricePerUnit;
        this.supplyDate = supplyDate;
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

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Date getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(Date supplyDate) {
        this.supplyDate = supplyDate;
    }
}
