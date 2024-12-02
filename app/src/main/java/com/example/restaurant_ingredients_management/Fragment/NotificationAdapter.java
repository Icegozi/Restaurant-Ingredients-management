package com.example.restaurant_ingredients_management.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.restaurant_ingredients_management.Controller.QuanLyThongBaoController;
import com.example.restaurant_ingredients_management.Model.StockAlert;
import com.example.restaurant_ingredients_management.R;
import com.example.restaurant_ingredients_management.View.NguyenLieu;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends BaseAdapter {
    private Context context;
    private List<StockAlert> alerts;
    private QuanLyThongBaoController controller;
    private boolean isReadonly;

    public NotificationAdapter(Context context, List<StockAlert> alerts, boolean isReadonly) {
        this.context = context;
        this.alerts = alerts;
        this.controller = new QuanLyThongBaoController(context);
        this.isReadonly = isReadonly;
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
            convertView.setBackgroundColor(context.getResources().getColor(R.color.resolved_background));
            tvTitle.setTextColor(context.getResources().getColor(R.color.resolved_text));
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.unresolved_background));
            tvTitle.setTextColor(context.getResources().getColor(R.color.unresolved_text));
        }

        // Nếu không phải chế độ readonly, cho phép thay đổi trạng thái
        if (!isReadonly) {
            convertView.setOnClickListener(v -> {
                if (!alert.isResolved()) {
                    alert.setResolved(true);
                    controller.resolveStockAlert(alert.getId());
                    Toast.makeText(context, "Đã đánh dấu thông báo: " + alert.getAlertType(), Toast.LENGTH_SHORT).show();
                } else {
                    alert.setResolved(false);
                    controller.unResolveStockAlert(alert.getId());
                    Toast.makeText(context, "Bỏ đánh dấu thông báo: " + alert.getAlertType(), Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
            });
        } else {
            convertView.setOnClickListener(null); // Vô hiệu hóa click nếu là readonly
        }

        // Vẫn cho phép xóa trong chế độ readonly
        convertView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa thông báo này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        controller.deleteStockAlertById(alert.getId());
                        alerts.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Đã xóa thông báo: " + alert.getAlertType(), Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                    .setCancelable(false)
                    .show();
            return true;
        });

        return convertView;
    }
}
