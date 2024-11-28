package com.example.restaurant_ingredients_management.Controller;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.restaurant_ingredients_management.Database.QuanLyNguyenLieuDBO;
import com.example.restaurant_ingredients_management.Model.Ingredient;
import com.example.restaurant_ingredients_management.Model.IngredientSupplier;
import com.example.restaurant_ingredients_management.Model.Supplier;
import com.example.restaurant_ingredients_management.R;
import com.example.restaurant_ingredients_management.View.NguyenLieu;

import java.util.List;

public class QuanLyNguyenLieuController {

    private QuanLyNguyenLieuDBO qlnlDBO;

    // Cần truyền context vào khi khởi tạo QuanLyNguyenLieuDBO
    public QuanLyNguyenLieuController(Activity activity) {
        qlnlDBO = new QuanLyNguyenLieuDBO(activity); // Truyền Activity vào
    }

    // Thêm nguyên liệu
    public long themNguyenLieu(Ingredient ingredient) {
        qlnlDBO.open();
        try {
            // Thêm nguyên liệu và lấy ID của nguyên liệu vừa thêm
            long ingredientId = qlnlDBO.insertIngredient(ingredient);
            if (ingredientId == -1)
                return 0; // Thêm nguyên liệu thất bại

            return ingredientId; // Thành công
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi nếu có
            return 0; // Trả về false nếu có lỗi
        } finally {
            qlnlDBO.close();
        }
    }

    //Thêm liên kết với nhà cung cấp
    public boolean themLienKet(IngredientSupplier ingredient) {
        qlnlDBO.open();
        try {
            Log.d("DEBUG", "ID nguyên liệu: " + ingredient.getIngredientId());
            Log.d("DEBUG", "ID nhà cung cấp: " + ingredient.getSupplierId());
            // Thêm nguyên liệu và lấy ID của nguyên liệu vừa thêm
            long IngredientSupplierId = qlnlDBO.insertIngredientSupplier(ingredient);
            if (IngredientSupplierId == -1)
                return false; // Thêm nguyên liệu thất bại

            return true; // Thành công
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi nếu có
            return false; // Trả về false nếu có lỗi
        } finally {
            qlnlDBO.close();
        }
    }
    // Lấy ID nhà cung cấp từ danh sách dựa trên tên
    public int getSupplierIdByName(String supplierName) {
        try {
            qlnlDBO.open(); // Mở cơ sở dữ liệu
            List<Supplier> suppliers = qlnlDBO.getAllSuppliers();

            // Kiểm tra nếu danh sách suppliers không null hoặc rỗng
            if (suppliers != null && !suppliers.isEmpty()) {
                for (Supplier supplier : suppliers) {
                    if (supplier.getName().equalsIgnoreCase(supplierName)) {
                        return supplier.getId();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi để xem chi tiết nếu có
        } finally {
            qlnlDBO.close(); // Đảm bảo đóng cơ sở dữ liệu
        }

        // Trả về -1 nếu không tìm thấy
        return -1;
    }

    // Lấy tất cả nguyên liệu
    public List<Ingredient> getAllIngredient() {
        qlnlDBO.open();
        try {
            return qlnlDBO.getAllIngredients();
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi nếu có
            return null; // Trả về null nếu có lỗi
        } finally {
            qlnlDBO.close(); // Đảm bảo đóng cơ sở dữ liệu
        }
    }

    //lấy tên nguyên liệu theo id
    public int getIdByNameIngredient(String name){
        List<Ingredient> Temp = getAllIngredient();
        for (Ingredient f: Temp) {
            if(f.getName().equalsIgnoreCase(name)){
                return f.getId();
            }
        }
        return -1;
    }

    // Xóa nguyên liệu theo id
    public boolean deleteIngredient(int id) {
        qlnlDBO.open();

        try {
            int rowsDeleted = qlnlDBO.deleteIngredient(id);
            if (rowsDeleted > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            qlnlDBO.close();
        }
    }

}