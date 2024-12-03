package com.example.restaurant_ingredients_management.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.restaurant_ingredients_management.Controller.QuanLyNguyenLieuController;
import com.example.restaurant_ingredients_management.MainActivity;
import com.example.restaurant_ingredients_management.Model.Ingredient;
import com.example.restaurant_ingredients_management.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class HinhAnh extends AppCompatActivity {
    private Spinner spNguyenLieu;
    private ImageView imgNguyenLieu;
    private Button btnLuu, btnHienThi;
    private ImageButton btnThem;
    private QuanLyNguyenLieuController qlnl;
    private ArrayList<String> nguyenLieuArrayList;
    private ArrayAdapter<String> nguyenLieuAdapter;

    private ActivityResultLauncher<Intent> getImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                            // Giảm kích thước ảnh nếu cần thiết
                            Bitmap resizedBitmap = resizeBitmap(bitmap, 800, 600);
                            imgNguyenLieu.setImageBitmap(resizedBitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Không thể hiển thị hình ảnh đã chọn!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
    );

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

        anhXaThuocTinh();
        nguyenLieuSpinner();
        action();
    }

    private void anhXaThuocTinh() {
        spNguyenLieu = findViewById(R.id.spTenNL);
        imgNguyenLieu = findViewById(R.id.imgNguyenLieu);
        btnLuu = findViewById(R.id.btnLuuAnh);
        btnHienThi = findViewById(R.id.btnHienThiAnh);
        btnThem = findViewById(R.id.btnThemAnh);
        qlnl = new QuanLyNguyenLieuController(this);
    }

    private void nguyenLieuSpinner() {
        nguyenLieuArrayList = new ArrayList<>();
        for (Ingredient ingredient : qlnl.getAllIngredient()) {
            nguyenLieuArrayList.add(ingredient.getName());
        }
        nguyenLieuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nguyenLieuArrayList);
        spNguyenLieu.setAdapter(nguyenLieuAdapter);
    }

    private void action() {
        btnHienThi.setOnClickListener(view -> hienThiHinhAnh());
        btnThem.setOnClickListener(view -> themHinhAnh());
        btnLuu.setOnClickListener(view -> luuHinhAnh());
    }

    private void hienThiHinhAnh() {
        String selectedIngredient = (String) spNguyenLieu.getSelectedItem();
        if (selectedIngredient != null) {
            int idNguyenLieu = 0;
            for (Ingredient ingredient : qlnl.getAllIngredient()) {
                if (ingredient.getName().equalsIgnoreCase(selectedIngredient)) {
                    idNguyenLieu = ingredient.getId();
                    break;
                }
            }
            byte[] imageData = qlnl.getIngredientImage(idNguyenLieu);
            if (imageData != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                imgNguyenLieu.setImageBitmap(bitmap);
            } else {
                Toast.makeText(this, "Không tìm thấy hình ảnh cho nguyên liệu này!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Vui lòng chọn nguyên liệu!", Toast.LENGTH_SHORT).show();
        }
    }

    private void themHinhAnh() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getImageLauncher.launch(intent);
    }

    private void luuHinhAnh() {
        String selectedIngredient = (String) spNguyenLieu.getSelectedItem();
        if (selectedIngredient != null) {
            int idNguyenLieu = 0;
            for (Ingredient ingredient : qlnl.getAllIngredient()) {
                if (ingredient.getName().equalsIgnoreCase(selectedIngredient)) {
                    idNguyenLieu = ingredient.getId();
                    break;
                }
            }
            if (idNguyenLieu > 0) {
                imgNguyenLieu.setDrawingCacheEnabled(true);
                imgNguyenLieu.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imgNguyenLieu.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream); // Giảm chất lượng hình ảnh để giảm kích thước
                byte[] imageData = stream.toByteArray();

                boolean isSaved = qlnl.saveIngredientImage(idNguyenLieu, imageData);
                if (isSaved) {
                    Toast.makeText(this, "Lưu hình ảnh thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Lưu hình ảnh thất bại!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Nguyên liệu không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Vui lòng chọn nguyên liệu!", Toast.LENGTH_SHORT).show();
        }
    }

    // Hàm để giảm kích thước ảnh nếu cần thiết
    private Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.opGiaoDich) {
            Intent intent = new Intent(HinhAnh.this, GiaoDich.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.opNguyenLieu) {
            Intent intent = new Intent(HinhAnh.this, NguyenLieu.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.opNhaCungCap) {
            Intent intent = new Intent(HinhAnh.this, NhaCungCap.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.opThongBao) {
            Intent intent = new Intent(HinhAnh.this, ThongBao.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.opTrangChu) {
            Intent intent = new Intent(HinhAnh.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.opHinhAnh) {
            Intent intent = new Intent(HinhAnh.this, HinhAnh.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
