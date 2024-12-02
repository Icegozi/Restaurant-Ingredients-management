package com.example.restaurant_ingredients_management.Workers;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.restaurant_ingredients_management.Controller.QuanLyThongBaoController;
import com.example.restaurant_ingredients_management.Model.Ingredient;
import com.example.restaurant_ingredients_management.Utils.AlertUtils;

import java.util.List;

public class CheckStockWorker extends Worker {

    public CheckStockWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Lấy danh sách nguyên liệu từ cơ sở dữ liệu thông qua QuanLyThongBaoController
        QuanLyThongBaoController controller = new QuanLyThongBaoController(getApplicationContext());
        List<Ingredient> ingredients = controller.getAllIngredient();

        // Kiểm tra hạn sử dụng và số lượng của nguyên liệu
        AlertUtils.checkIngredients(getApplicationContext(), ingredients);

        // test trong 1 phút
//        // Lập lịch lại công việc
//        WorkManager.getInstance(getApplicationContext())
//                .enqueue(new OneTimeWorkRequest.Builder(CheckStockWorker.class)
//                        .setInitialDelay(1, TimeUnit.MINUTES) // Chạy lại sau 1 phút
//                        .build());
//        return Result.success();

        return Result.success();
    }
}
