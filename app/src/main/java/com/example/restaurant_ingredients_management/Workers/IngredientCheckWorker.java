package com.example.restaurant_ingredients_management.Workers;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.restaurant_ingredients_management.Utils.AlertUtils;
import com.example.restaurant_ingredients_management.Model.Ingredient;

import java.util.List;

public class IngredientCheckWorker extends Worker {

    public IngredientCheckWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Lấy danh sách nguyên liệu từ cơ sở dữ liệu hoặc một nguồn nào đó
        List<Ingredient> ingredients = fetchIngredients();

        // Kiểm tra và gửi thông báo nếu cần
        AlertUtils.checkIngredients(getApplicationContext(), ingredients);

        return Result.success();
    }

    private List<Ingredient> fetchIngredients() {
        // Lấy danh sách nguyên liệu từ cơ sở dữ liệu
        // Triển khai tùy thuộc vào cấu trúc ứng dụng của bạn
        return null; // Trả về danh sách thực tế
    }
}
