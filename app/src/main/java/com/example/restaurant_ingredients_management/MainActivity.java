package com.example.restaurant_ingredients_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant_ingredients_management.View.GiaoDich;
import com.example.restaurant_ingredients_management.View.HinhAnh;
import com.example.restaurant_ingredients_management.View.NguyenLieu;
import com.example.restaurant_ingredients_management.View.NhaCungCap;
import com.example.restaurant_ingredients_management.View.ThongBao;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        setTitle("Trang chủ");

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
            Intent intent = new Intent(MainActivity.this, GiaoDich.class);
            startActivity(intent);
        }
        if(id==R.id.opNguyenLieu){
            Intent intent = new Intent(MainActivity.this, NguyenLieu.class);
            startActivity(intent);
        }
        if(id==R.id.opNhaCungCap){
            Intent intent = new Intent(MainActivity.this, NhaCungCap.class);
            startActivity(intent);
        }
        if(id==R.id.opThongBao){
            Intent intent = new Intent(MainActivity.this, ThongBao.class);
            startActivity(intent);
        }
        if(id==R.id.opHinhAnh){
            Intent intent = new Intent(MainActivity.this, HinhAnh.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}