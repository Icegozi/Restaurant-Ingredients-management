package com.example.restaurant_ingredients_management.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.restaurant_ingredients_management.MainActivity;
import com.example.restaurant_ingredients_management.R;

public class HinhAnh extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hinh_anh);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setTitle("Hình ảnh");
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
            Intent intent = new Intent(HinhAnh.this, GiaoDich.class);
            startActivity(intent);
        }
        if(id==R.id.opNguyenLieu){
            Intent intent = new Intent(HinhAnh.this, NguyenLieu.class);
            startActivity(intent);
        }
        if(id==R.id.opNhaCungCap){
            Intent intent = new Intent(HinhAnh.this, NhaCungCap.class);
            startActivity(intent);
        }
        if(id==R.id.opThongBao){
            Intent intent = new Intent(HinhAnh.this, ThongBao.class);
            startActivity(intent);
        }
        if(id==R.id.opTrangChu){
            Intent intent = new Intent(HinhAnh.this, MainActivity.class);
            startActivity(intent);
        }
        if (id==R.id.opHinhAnh){
            Intent intent = new Intent(HinhAnh.this, HinhAnh.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}