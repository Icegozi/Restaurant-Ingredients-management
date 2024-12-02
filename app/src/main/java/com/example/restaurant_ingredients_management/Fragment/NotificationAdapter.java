package com.example.restaurant_ingredients_management.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.restaurant_ingredients_management.Controller.QuanLyThongBaoController;
import com.example.restaurant_ingredients_management.Model.StockAlert;
import com.example.restaurant_ingredients_management.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends BaseAdapter {
    private Context context;
    private List<StockAlert> alerts;
    private QuanLyThongBaoController controller;

    public NotificationAdapter(Context context, List<StockAlert> alerts) {
        this.context = context;
        this.alerts = alerts;
        this.controller = new QuanLyThongBaoController(context);
    }

    @Override
    public int getCount() {
        return alerts.size();
    }

    @Override
    public Object getItem(int position) {
        return alerts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        }

        StockAlert alert = alerts.get(position);

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvTimestamp = convertView.findViewById(R.id.tvTimestamp);

        tvTitle.setText(alert.getAlertType());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        tvTimestamp.setText(sdf.format(alert.getAlertDate()));

        // Thay đổi màu sắc dựa trên trạng thái đã giải quyết
        if (alert.isResolved()) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.resolved_background)); // Thay bằng màu cho cảnh báo đã giải quyết
            tvTitle.setTextColor(context.getResources().getColor(R.color.resolved_text)); // Màu chữ cho cảnh báo đã giải quyết
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.unresolved_background)); // Màu nền cho cảnh báo chưa giải quyết
            tvTitle.setTextColor(context.getResources().getColor(R.color.unresolved_text)); // Màu chữ cho cảnh báo chưa giải quyết
        }

        // Handle click to resolve alert
        convertView.setOnClickListener(v -> {
            if (!alert.isResolved()) {
                alert.setResolved(true);
                controller.resolveStockAlert(alert.getId());
                Toast.makeText(context, "Đã giải quyết cảnh báo: " + alert.getAlertType(), Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "Cảnh báo đã được giải quyết", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

}
