package com.example.restaurant_ingredients_management.Utils;

import android.content.Context;
import com.example.restaurant_ingredients_management.Controller.QuanLyThongBaoController;
import com.example.restaurant_ingredients_management.Model.Ingredient;
import com.example.restaurant_ingredients_management.Model.StockAlert;

import java.util.List;

public class AlertUtils {

    public static void checkIngredients(Context context, List<Ingredient> ingredients) {
        QuanLyThongBaoController alertController = new QuanLyThongBaoController(context);

        for (Ingredient ingredient : ingredients) {
            // Kiểm tra hết hạn
            if (ingredient.isExpired()) {
                StockAlert expirationAlert = new StockAlert(
                        ingredient.getId(),
                        ingredient.getName()+" sắp hết hạn",
                        System.currentTimeMillis(),
                        false
                );
                alertController.addStockAlert(expirationAlert);

                NotificationUtils.showNotification(
                        context,
                        "Hạn sử dụng",
                        "Nguyên liệu " + ingredient.getName() + " đã hết hạn!"
                );
            }

            // Kiểm tra số lượng thấp và gửi thông báo nếu số lượng thấp
                if (ingredient.isLowStock()) {
                    StockAlert lowStockAlert = new StockAlert(
                            ingredient.getId(),
                            ingredient.getName()+" sắp hết hàng",
                            System.currentTimeMillis(),
                            false
                    );
                    alertController.addStockAlert(lowStockAlert);

                    NotificationUtils.showNotification(
                            context,
                            "Số lượng",
                            "Nguyên liệu " + ingredient.getName() + " sắp hết hàng!"
                    );
                }
            }
        }
}
