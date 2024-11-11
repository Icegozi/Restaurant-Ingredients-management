package com.example.restaurant_ingredients_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.restaurant_ingredients_management.View.GiaoDich;
import com.example.restaurant_ingredients_management.View.NhaCungCap;
import com.example.restaurant_ingredients_management.View.TongQuan;

public class MainActivity extends AppCompatActivity {
    private Button btn,btn_chuyen, btn_ncc;

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
         btn = findViewById(R.id.test);
         btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent next = new Intent(MainActivity.this, TongQuan.class);
                    startActivity(next);
             }
         });

        btn_chuyen = findViewById(R.id.btn_chuyen);
        btn_ncc = findViewById(R.id.btn_ncc);
        btn_ncc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(MainActivity.this, NhaCungCap.class);
                startActivity(myintent);
            }
        });
        btn_chuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(MainActivity.this, GiaoDich.class);
                startActivity(myintent);
            }
        });
        setTitle("Giao Dịch");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}