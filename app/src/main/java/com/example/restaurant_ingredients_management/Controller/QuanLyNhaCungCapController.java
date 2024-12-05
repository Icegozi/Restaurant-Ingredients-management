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
//        supplierDBO.open(); // Mở kết nối cơ sở dữ liệu
    }

    // Thêm nhà cung cấp
    public long addSupplier(Supplier supplier) {

        supplierDBO.open();
        try {
            return supplierDBO.insertSupplier(supplier);
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi nếu có
            return -1; // Trả về null nếu có lỗi
        } finally {
            supplierDBO.close(); // Đảm bảo đóng cơ sở dữ liệu
        }
    }

    // Sửa thông tin nhà cung cấp
    public int updateSupplier(Supplier supplier) {

        supplierDBO.open();
        try {
            return supplierDBO.updateSupplier(supplier);
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi nếu có
            return -1; // Trả về null nếu có lỗi
        } finally {
            supplierDBO.close(); // Đảm bảo đóng cơ sở dữ liệu
        }
    }

    // Xóa nhà cung cấp theo ID
    public int deleteSupplier(int supplierId) {

        supplierDBO.open();
        try {
            return supplierDBO.deleteSupplier(supplierId);
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi nếu có
            return -1; // Trả về null nếu có lỗi
        } finally {
            supplierDBO.close(); // Đảm bảo đóng cơ sở dữ liệu
        }
    }

    // Lấy danh sách tất cả các nhà cung cấp
    public ArrayList<Supplier> getAllSuppliers() {
        supplierDBO.open();
        try {
            return supplierDBO.getAllSuppliers();
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi nếu có
            return null; // Trả về null nếu có lỗi
        } finally {
            supplierDBO.close(); // Đảm bảo đóng cơ sở dữ liệu
        }
    }

    // Tìm kiếm nhà cung cấp theo tên
    public ArrayList<Supplier> searchSuppliers(String keyword) {
        supplierDBO.open();
        try {
            return supplierDBO.searchSupplierByName(keyword);
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi nếu có
            return null; // Trả về null nếu có lỗi
        } finally {
            supplierDBO.close(); // Đảm bảo đóng cơ sở dữ liệu
        }

    }
    //lấy nhà cung cấp theo ten
    public Supplier getSupplierByName(String supplierName)
    {
        supplierDBO.open();
        try {
            return supplierDBO.getSupplierByName(supplierName);
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi nếu có
            return null; // Trả về null nếu có lỗi
        } finally {
            supplierDBO.close(); // Đảm bảo đóng cơ sở dữ liệu
        }
    }
    // Đóng kết nối cơ sở dữ liệu
    public void close() {
        supplierDBO.close();
    }

}
