package com.example.restaurant_ingredients_management.Controller;

import android.content.Context;

import com.example.restaurant_ingredients_management.Database.QuanLyGiaoDichDBO;
import com.example.restaurant_ingredients_management.Database.QuanLyNhaCungCap_NguyenLieuDBO;
import com.example.restaurant_ingredients_management.Model.Transaction;

import java.util.ArrayList;

public class QuanLyGiaoDIchController {
    private QuanLyGiaoDichDBO transactionDBO;
    private QuanLyNhaCungCap_NguyenLieuDBO nhaCCCNguyenLieuDBO;

    public QuanLyGiaoDIchController(Context context) {
        transactionDBO = new QuanLyGiaoDichDBO(context);
        transactionDBO.open(); // Mở kết nối cơ sở dữ liệu
    }


    // Thêm giao dịch
    public long addTransaction(Transaction transaction) {
        return transactionDBO.addTransaction(transaction);
    }

    // Sửa thông tin giao dịch
    public int updateTransaction(Transaction transaction) {
        return transactionDBO.updateTransaction(transaction);
    }

    // Xóa giao dịch theo ID
    public int deleteTransaction(int transactionId) {
        return transactionDBO.deleteTransaction(transactionId);
    }

    public void capNhatSoLuongNguyenLieuSauKhiXoa(Transaction transaction){
        transactionDBO.capNhatSoLuongNguyenLieuKhiXoa(transaction);
    }
    // Lấy danh sách tất cả các giao dịch
    public ArrayList<Transaction> getAllTransactions() {
        return transactionDBO.getAllTransactions();
    }

    // Đóng kết nối cơ sở dữ liệu
    public void close() {
        transactionDBO.close();
    }

    //lay tat ca ten nha cung cap
    public ArrayList<String> getAllSupplierNames(){
        return transactionDBO.getAllSupplierNames();
    }

    //lay tat ca ten nguyen lieu
    public ArrayList<String> getAllIngredientNames(){
        return transactionDBO.getAllIngredientNames();
    }

    //tim kiem id theo ten nguyen lieu
    public int LayIdNguyenLieu(String name){
        return transactionDBO.layIdNguyenLieu(name);
    }

    public ArrayList<Transaction> searchTransactionsByDate(String date)
    {
        return transactionDBO.searchTransactionsByDate(date);
    }

    public int LaySoLuongTrongKho(int ingredientId)
    {
        return transactionDBO.getCurrentIngredientQuantity(ingredientId);
    }

    public boolean updateIngredientQuantityById(int ingredientId, double newQuantity)
    {
        return transactionDBO.updateIngredientQuantityById(ingredientId, newQuantity);
    }

    // Lấy giá cả nguyên liệu theo ID nhà cung cấp và nguyên liệu
    public double layGiaCaNguyenLieu(int ingredientId, int supplierId) {
        return transactionDBO.layGiaCaNguyenLieu(ingredientId, supplierId);
    }



}
