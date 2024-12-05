package com.example.restaurant_ingredients_management.Controller;

import android.content.Context;

import com.example.restaurant_ingredients_management.Database.QuanLyGiaoDichDBO;
import com.example.restaurant_ingredients_management.Database.QuanLyNhaCungCapDBO;
import com.example.restaurant_ingredients_management.Database.QuanLyNhaCungCap_NguyenLieuDBO;

public class QuanLyNhaCC_NguyenLieuController {
    private QuanLyNhaCungCap_NguyenLieuDBO indredientSuppliereDBO;

    public QuanLyNhaCC_NguyenLieuController(Context context) {
        indredientSuppliereDBO = new QuanLyNhaCungCap_NguyenLieuDBO(context);
        indredientSuppliereDBO.open(); // Mở kết nối cơ sở dữ liệu
    }
    //lay gia ca theo id nguyen lieu va nha cung cap nguyen lieu
    public double getPriceBySupplierAndIngredient(int supplierId, int ingredientId)
    {
        indredientSuppliereDBO.open();
        try {
            return indredientSuppliereDBO.getPriceBySupplierAndIngredient(supplierId, ingredientId);
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi nếu có
            return -1; // Trả về null nếu có lỗi
        } finally {
            indredientSuppliereDBO.close(); // Đảm bảo đóng cơ sở dữ liệu
        }
    }

    public void close() {
        indredientSuppliereDBO.close();
    }
}
