package com.example.restaurant_ingredients_management.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.restaurant_ingredients_management.MainActivity;
import com.example.restaurant_ingredients_management.R;

public class GiaoDich extends AppCompatActivity {

    Button btn_them_giao_dich, btn_lich_su_giao_dich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_giao_dich);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setTitle("Giao dich");
        ViewSwitcher viewSwitcher = findViewById(R.id.viewSwitcher);
        btn_lich_su_giao_dich = findViewById(R.id.btn_lich_su_giao_dich);
        btn_them_giao_dich = findViewById(R.id.btn_them_giao_dich);
        btn_lich_su_giao_dich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSwitcher.setDisplayedChild(0);
            }
        });
        btn_them_giao_dich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSwitcher.setDisplayedChild(1);
            }
        });

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
            Intent intent = new Intent(GiaoDich.this, GiaoDich.class);
            startActivity(intent);
            finish();
        }
        if(id==R.id.opNguyenLieu){
            Intent intent = new Intent(GiaoDich.this, NguyenLieu.class);
            startActivity(intent);
            finish();
        }
        if(id==R.id.opNhaCungCap){
            Intent intent = new Intent(GiaoDich.this, NhaCungCap.class);
            startActivity(intent);
            finish();
        }
        if(id==R.id.opThongBao){
            Intent intent = new Intent(GiaoDich.this, ThongBao.class);
            startActivity(intent);
            finish();
        }
        if(id==R.id.opTrangChu){
            Intent intent = new Intent(GiaoDich.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (id==R.id.opHinhAnh){
            Intent intent = new Intent(GiaoDich.this, HinhAnh.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}