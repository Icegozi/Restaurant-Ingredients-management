package com.example.restaurant_ingredients_management.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.restaurant_ingredients_management.Controller.QuanLyNguyenLieuController;
import com.example.restaurant_ingredients_management.Controller.QuanLyThongBaoController;
import com.example.restaurant_ingredients_management.Fragment.NotificationAdapter;
import com.example.restaurant_ingredients_management.MainActivity;
import com.example.restaurant_ingredients_management.Model.Ingredient;
import com.example.restaurant_ingredients_management.Model.StockAlert;
import com.example.restaurant_ingredients_management.R;
import com.example.restaurant_ingredients_management.Utils.AlertUtils;

import java.util.List;

public class ThongBao extends AppCompatActivity {

    private ListView lvThongBao;
    private QuanLyThongBaoController thongBaoController;
    private QuanLyNguyenLieuController nguyenLieuController;
    private NotificationAdapter notificationAdapter; // Custom adapter cho thông báo
    private ImageButton btnXoa,btnStar,btnRestart;
    private TextView tvSoLuong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_bao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setTitle("Thông báo");

        // Ánh xạ view
        anhXaThuocTinh();
        // Tự động kiểm tra nguyên liệu khi mở ứng dụng
//        nguyenLieuController.checkAndUpdateAlerts(this);
        // Hiển thị danh sách thông báo
        loadNotifications();
        // Xóa tất cả thông báo
        removeAllNotification();
        // Hiển thị thông báo chưa đánh dấu
        showUnResolvedAlerts();
    }

    private void anhXaThuocTinh(){
        lvThongBao = findViewById(R.id.lvThongBao);
        btnXoa = findViewById(R.id.btnXoa);
        btnRestart = findViewById(R.id.btnRestart);
        btnStar = findViewById(R.id.btnStar);
        tvSoLuong = findViewById(R.id.tvSoLuong);
        // Khởi tạo controller
        thongBaoController = new QuanLyThongBaoController(this);
        nguyenLieuController = new QuanLyNguyenLieuController(this);
    }

    private void showUnResolvedAlerts(){
        btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<StockAlert> alerts = thongBaoController.getUnresolvedAlerts();
                notificationAdapter = new NotificationAdapter(view.getContext(), alerts, true); // Chế độ readonly
                lvThongBao.setAdapter(notificationAdapter);
                tvSoLuong.setText("Chưa xử lý: "+ alerts.size() );
            }
        });


        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNotifications();
            }
        });
    }

    private void removeAllNotification(){
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Tạo dialog xác nhận trước khi xóa
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa tất cả thông báo không? ")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                thongBaoController.clearAllStockAlerts();
                                loadNotifications();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Nếu người dùng chọn "Không", không làm gì cả
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false) // Không cho phép đóng dialog ngoài thao tác với nút
                        .show();
            }
        });
    }



    private void loadNotifications() {
        List<StockAlert> alerts = thongBaoController.getAllStockAlerts();
        notificationAdapter = new NotificationAdapter(this, alerts,false);
        lvThongBao.setAdapter(notificationAdapter);
        tvSoLuong.setText("Bạn có "+ alerts.size() + " thông báo.");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.opGiaoDich){
            Intent intent = new Intent(ThongBao.this, GiaoDich.class);
            startActivity(intent);
            finish();
        }
        if(id==R.id.opNguyenLieu){
            Intent intent = new Intent(ThongBao.this, NguyenLieu.class);
            startActivity(intent);
            finish();
        }
        if(id==R.id.opNhaCungCap){
            Intent intent = new Intent(ThongBao.this, NhaCungCap.class);
            startActivity(intent);
            finish();
        }
        if(id==R.id.opThongBao){
            Intent intent = new Intent(ThongBao.this, ThongBao.class);
            startActivity(intent);
            finish();
        }
        if(id==R.id.opTrangChu){
            Intent intent = new Intent(ThongBao.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (id==R.id.opHinhAnh){
            Intent intent = new Intent(ThongBao.this, HinhAnh.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}