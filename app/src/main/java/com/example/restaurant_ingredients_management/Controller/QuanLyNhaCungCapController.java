package com.example.restaurant_ingredients_management.Controller;

import android.content.Context;

import com.example.restaurant_ingredients_management.Database.QuanLyNhaCungCapDBO;
import com.example.restaurant_ingredients_management.Model.Supplier;

import java.util.ArrayList;
import java.util.List;

public class QuanLyNhaCungCapController {
    private QuanLyNhaCungCapDBO supplierDBO;

    public QuanLyNhaCungCapController(Context context) {
        supplierDBO = new QuanLyNhaCungCapDBO(context);
        supplierDBO.open(); // Mở kết nối cơ sở dữ liệu
    }

    // Thêm nhà cung cấp
    public long addSupplier(Supplier supplier) {
        return supplierDBO.insertSupplier(supplier);
    }

    // Sửa thông tin nhà cung cấp
    public int updateSupplier(Supplier supplier) {
        return supplierDBO.updateSupplier(supplier);
    }

    // Xóa nhà cung cấp theo ID
    public int deleteSupplier(int supplierId) {
        return supplierDBO.deleteSupplier(supplierId);
    }

    // Lấy danh sách tất cả các nhà cung cấp
    public ArrayList<Supplier> getAllSuppliers() {
        return supplierDBO.getAllSuppliers();
    }

    // Tìm kiếm nhà cung cấp theo tên
    public ArrayList<Supplier> searchSuppliers(String keyword) {
        return supplierDBO.searchSupplierByName(keyword);
    }

    // Đóng kết nối cơ sở dữ liệu
    public void close() {
        supplierDBO.close();
    }
}
