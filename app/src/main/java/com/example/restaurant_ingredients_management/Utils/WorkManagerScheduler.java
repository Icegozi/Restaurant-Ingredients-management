package com.example.restaurant_ingredients_management.Utils;

import android.content.Context;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.ExistingPeriodicWorkPolicy;
import java.util.concurrent.TimeUnit;

import com.example.restaurant_ingredients_management.Workers.CheckStockWorker;



public class WorkManagerScheduler {

    public static void scheduleStockCheck(Context context) {
        // Tạo yêu cầu công việc định kỳ
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
                CheckStockWorker.class,
                15, TimeUnit.MINUTES // Lặp lại mỗi 15 phút (thời gian tối thiểu)
        ).build();

        // Lên lịch công việc với WorkManager
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "StockCheckWork", // Tên duy nhất của công việc
                ExistingPeriodicWorkPolicy.KEEP, // Không tạo lại nếu đã tồn tại
                workRequest
        );

        //test trong 1 phút
//        OneTimeWorkRequest oneTimeWorkRequest =
//                new OneTimeWorkRequest.Builder(CheckStockWorker.class)
//                        .build();
//
//        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);
    }
}
