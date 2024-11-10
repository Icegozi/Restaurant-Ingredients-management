package com.example.restaurant_ingredients_management.View;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.restaurant_ingredients_management.MainActivity;
import com.example.restaurant_ingredients_management.R;

import java.util.Calendar;

public class TongQuan extends AppCompatActivity {

    private EditText editTextDate;
    private ImageButton buttonDatePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tong_quan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextDate = findViewById(R.id.ptDate);
        buttonDatePicker = findViewById(R.id.imgDate);

        // Sự kiện nhấp vào EditText hoặc nút hình ảnh
        buttonDatePicker.setOnClickListener(view -> showDatePicker());
        editTextDate.setOnClickListener(view -> showDatePicker());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TongQuan.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Cập nhật EditText với định dạng "yyyy / MM / dd"
                    String date = String.format("%04d / %02d / %02d", selectedYear, selectedMonth + 1, selectedDay);
                    editTextDate.setText(date);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}