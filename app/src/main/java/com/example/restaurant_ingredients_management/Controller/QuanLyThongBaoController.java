package com.example.restaurant_ingredients_management.Controller;

import android.content.Context;
import com.example.restaurant_ingredients_management.Database.QuanLyThongBaoDBO;
import com.example.restaurant_ingredients_management.Model.StockAlert;
import java.util.List;

public class QuanLyThongBaoController {
    private QuanLyThongBaoDBO thongBaoDBO;

    public QuanLyThongBaoController(Context context) {
        thongBaoDBO = new QuanLyThongBaoDBO(context);
    }

    // Thêm cảnh báo mới
    public void addStockAlert(StockAlert alert) {
        thongBaoDBO.open();
        try {
            thongBaoDBO.insertStockAlert(alert);
        } finally {
            thongBaoDBO.close();
        }
    }

    // Lấy danh sách tất cả cảnh báo
    public List<StockAlert> getAllStockAlerts() {
        thongBaoDBO.open();
        try {
            return thongBaoDBO.getAllStockAlerts();
        } finally {
            thongBaoDBO.close();
        }
    }

    // Lấy danh sách cảnh báo chưa xử lý
    public List<StockAlert> getUnresolvedAlerts() {
        thongBaoDBO.open();
        try {
            return thongBaoDBO.getUnresolvedAlerts();
        } finally {
            thongBaoDBO.close();
        }
    }

    // Đánh dấu cảnh báo là đã xử lý
    public void resolveStockAlert(int alertId) {
        thongBaoDBO.open();
        try {
            thongBaoDBO.resolveAlert(alertId);
        } finally {
            thongBaoDBO.close();
        }
    }

    // Xóa tất cả cảnh báo
    public void clearAllStockAlerts() {
        thongBaoDBO.open();
        try {
            thongBaoDBO.deleteAllAlerts();
        } finally {
            thongBaoDBO.close();
        }
    }
}
