
package com.example.restaurant_ingredients_management.View;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.restaurant_ingredients_management.Controller.QuanLyNguyenLieuController;
import com.example.restaurant_ingredients_management.MainActivity;
import com.example.restaurant_ingredients_management.Model.Ingredient;
import com.example.restaurant_ingredients_management.Model.IngredientSupplier;
import com.example.restaurant_ingredients_management.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NguyenLieu extends AppCompatActivity {
    private EditText txtTenNguyenLieu, txtSoLuong, txtHanSuDung, txtGiaTien;
    private ImageButton btnSua, btnThem, btnDate;
    private ListView lvNguyenLieu;
    private Spinner spNhaCungCap, spDonViDo;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private QuanLyNguyenLieuController qlnl;
    private ArrayList<String> arrayDonVi, arrayNhaCungCap;
    private ArrayAdapter<String> DonViAdapter, NhaCungCapAdapter;
    private ArrayAdapter<Ingredient> NguyenLieuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nguyen_lieu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setTitle("Nguyên liệu");

        // Khởi tạo controller với context (this)
        qlnl = new QuanLyNguyenLieuController(this);

        anhXaThuocTinh();
        donViSpinner();
        nhaCungCapSpinner();
        nguyenLieuListView();
        action();
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
            startActivity(new Intent(NguyenLieu.this, GiaoDich.class));
            finish();
        }
        if (id == R.id.opNguyenLieu) {
            startActivity(new Intent(NguyenLieu.this, NguyenLieu.class));
            finish();
        }
        if (id == R.id.opNhaCungCap) {
            startActivity(new Intent(NguyenLieu.this, NhaCungCap.class));
            finish();
        }
        if (id == R.id.opThongBao) {
            startActivity(new Intent(NguyenLieu.this, ThongBao.class));
            finish();
        }
        if (id == R.id.opTrangChu) {
            startActivity(new Intent(NguyenLieu.this, MainActivity.class));
            finish();
        }
        if (id == R.id.opHinhAnh) {
            startActivity(new Intent(NguyenLieu.this, HinhAnh.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // Đổ dữ liệu vào spinner đơn vị
    private void donViSpinner() {
        arrayDonVi = new ArrayList<>();
        arrayDonVi.add("Kg");
        arrayDonVi.add("Gram");
        arrayDonVi.add("Lít");
        DonViAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayDonVi);
        spDonViDo.setAdapter(DonViAdapter);
    }

    // Đổ dữ liệu vào spinner nhà cung cấp
    private void nhaCungCapSpinner() {
        arrayNhaCungCap = new ArrayList<>();
        arrayNhaCungCap.add("Icegozi");
        arrayNhaCungCap.add("FKingDom");
        arrayNhaCungCap.add("Sunset");
        NhaCungCapAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayNhaCungCap);
        spNhaCungCap.setAdapter(NhaCungCapAdapter);
    }

    // Đổ dữ liệu vào listview nguyên liệu
    private void nguyenLieuListView() {
        try {
            NguyenLieuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, qlnl.getAllIngredient());
            lvNguyenLieu.setAdapter(NguyenLieuAdapter);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    // Ánh xạ các thuộc tính từ giao diện
    private void anhXaThuocTinh() {
        txtTenNguyenLieu = findViewById(R.id.tenNguyenLieu);
        txtSoLuong = findViewById(R.id.soLuong);
        txtGiaTien = findViewById(R.id.giaTien);
        txtHanSuDung = findViewById(R.id.hanSuDung);
        btnDate = findViewById(R.id.btnDate);
        btnSua = findViewById(R.id.btnSua);
        btnThem = findViewById(R.id.btnThem);
        spDonViDo = findViewById(R.id.spDonViDo);
        spNhaCungCap = findViewById(R.id.spNhaCungCap);
        lvNguyenLieu = findViewById(R.id.lvNguyenLieu);
    }

    // Các thao tác khi bấm vào các button
    private void action() {
        btnThem.setOnClickListener(view -> themNguyenLieu());
        btnDate.setOnClickListener(view -> showDatePickerDialog());
        lvNguyenLieu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                xoaNguyenLieu(i);
                return false;
            }
        });
    }

    // Hiển thị lịch để người dùng lựa chọn
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            txtHanSuDung.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void themNguyenLieu() {
        Log.d("DEBUG", "Bắt đầu thêm nguyên liệu");

        String tenNguyenLieu = txtTenNguyenLieu.getText().toString().trim();
        String soLuongText = txtSoLuong.getText().toString().trim();
        String giaTienText = txtGiaTien.getText().toString().trim();
        String hanSuDungText = txtHanSuDung.getText().toString().trim();
        String tenNhaCungCap = spNhaCungCap.getSelectedItem().toString();

        // Kiểm tra nếu dữ liệu nhập vào không đầy đủ
        if (tenNguyenLieu.isEmpty() || soLuongText.isEmpty() || giaTienText.isEmpty() || hanSuDungText.isEmpty()) {
            Log.d("DEBUG", "Thông tin không đầy đủ");
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        double soLuong;
        double giaTien;
        long hanSuDung;

        // Kiểm tra số lượng và giá tiền có hợp lệ hay không
        try {
            soLuong = Double.parseDouble(soLuongText);
            giaTien = Double.parseDouble(giaTienText);
        } catch (NumberFormatException e) {
            Log.e("ERROR", "Lỗi định dạng số", e);
            Toast.makeText(this, "Vui lòng nhập số hợp lệ cho số lượng và giá tiền!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra ngày hết hạn
        try {
            hanSuDung = sdf.parse(hanSuDungText).getTime();
        } catch (ParseException e) {
            Log.e("ERROR", "Lỗi khi phân tích ngày hết hạn", e);
            Toast.makeText(this, "Ngày hết hạn không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng nguyên liệu
        Ingredient ingredient = new Ingredient();
        ingredient.setName(tenNguyenLieu);
        ingredient.setQuantity(soLuong);
        ingredient.setUnit(spDonViDo.getSelectedItem().toString()); // Lấy đơn vị từ Spinner
        ingredient.setExpirationDate(hanSuDung);
        ingredient.setLowStock(soLuong < 10);
        ingredient.setLastUpdated(System.currentTimeMillis());


        Log.d("Selected Supplier", tenNhaCungCap);



        // Thêm nguyên liệu và liên kết vào cơ sở dữ liệu
        long idNguyenLieu = qlnl.themNguyenLieu(ingredient);

        boolean isSuccess = idNguyenLieu == 0 ? false : true;
        if (isSuccess) {
            Log.d("DEBUG", "Thêm nguyên liệu thành công");
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            // Cập nhật lại ListView sau khi thêm nguyên liệu
            nguyenLieuListView();
//            int idNhaCungCap = qlnl.getSupplierIdByName(tenNhaCungCap);
            int idNhaCungCap = 2;
            if (idNguyenLieu <= 0 || idNhaCungCap <= 0) {
                Log.e("ERROR", "Ingredient ID hoặc Supplier ID không hợp lệ");
                return; // Dừng lại nếu ID không hợp lệ
            }
            // Tạo đối tượng nhà cung cấp
            IngredientSupplier supplier = new IngredientSupplier(0, (int) idNguyenLieu, idNhaCungCap, giaTien, System.currentTimeMillis());
            boolean isIngredientSupplier = qlnl.themLienKet(supplier);
            if(isIngredientSupplier){
                resetText();
            }
        } else {
            Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    // Xóa nguyên liệu với xác nhận
    private void xoaNguyenLieu(int i) {
        // Lấy nguyên liệu cần xóa
        Ingredient ingredient = qlnl.getAllIngredient().get(i);

        // Tạo dialog xác nhận trước khi xóa
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa nguyên liệu này không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nếu người dùng chọn "Có", thực hiện xóa
                        boolean isSuccess = qlnl.deleteIngredient(ingredient.getId());
                        if (isSuccess) {
                            // Cập nhật lại ListView sau khi xóa nguyên liệu
                            nguyenLieuListView();
                            Toast.makeText(NguyenLieu.this, "Xóa nguyên liệu thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NguyenLieu.this, "Xóa nguyên liệu thất bại", Toast.LENGTH_SHORT).show();
                        }
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

    private void resetText(){
        txtGiaTien.setText("");
        txtSoLuong.setText("");
        txtTenNguyenLieu.setText("");
        txtHanSuDung.setText("");
        spNhaCungCap.setSelection(0);
        spDonViDo.setSelection(0);
    }
}
