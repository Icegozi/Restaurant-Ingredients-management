package com.example.restaurant_ingredients_management.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.restaurant_ingredients_management.Controller.QuanLyGiaoDIchController;
import com.example.restaurant_ingredients_management.Controller.QuanLyNguyenLieuController;
import com.example.restaurant_ingredients_management.Controller.QuanLyNhaCungCapController;
import com.example.restaurant_ingredients_management.MainActivity;
import com.example.restaurant_ingredients_management.Model.Ingredient;
import com.example.restaurant_ingredients_management.Model.Supplier;
import com.example.restaurant_ingredients_management.Model.Transaction;
import com.example.restaurant_ingredients_management.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GiaoDich extends AppCompatActivity {

    Button btn_them_giao_dich, btn_lich_su_giao_dich;
    ImageButton btn_them_gd, btn_chon_ngay_gd, btn_chinh_sua_gd;
    Spinner spn_ten_nguyenlieu, spn_donvido, spn_ten_nha_cc;
    EditText edt_soluong, edt_ngay_gd, edt_ghichu;
    RadioButton rd_nhap, rd_xuat;
    RadioGroup rd_nhap_xuat;
    QuanLyGiaoDIchController giaoDichController;
    QuanLyNhaCungCapController supplierController;
    QuanLyNguyenLieuController ingredientController;
    ArrayList<String> listTenNguyenLieu;
    ArrayList<String> listTenNhanCC;
    List<Ingredient> listIngredients;
    ArrayList<Supplier> listSuppliers;

    //lich su giao dich
    EditText edt_tk_ngaythang;
    ImageButton btn_lich_ngaythang_gd, btn_tk_giaodich;
    ListView lv_giaodich;
    ArrayList<Transaction> allTransactions = new ArrayList<>();
    ArrayAdapter<Transaction> transactionAdapter;

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
        setTitle("Quản lý nhập/xuất");
        ViewSwitcher viewSwitcher = findViewById(R.id.viewSwitcher);
        btn_lich_su_giao_dich = findViewById(R.id.btn_lich_su_giao_dich);
        btn_them_giao_dich = findViewById(R.id.btn_them_giao_dich);
        btn_them_gd = findViewById(R.id.btn_them_gd);
        btn_chon_ngay_gd = findViewById(R.id.btn_chon_ngay_gd);
        spn_ten_nguyenlieu = findViewById(R.id.spn_ten_nguyen_lieu);
        spn_donvido = findViewById(R.id.spn_donvido);
        spn_ten_nha_cc = findViewById(R.id.spn_ten_nha_cc);
        edt_soluong = findViewById(R.id.edt_so_luong_NX);
        edt_ghichu = findViewById(R.id.edt_nhap_ghi_chu);
        edt_ngay_gd = findViewById(R.id.edt_ngay_giao_dich);
        rd_nhap_xuat = findViewById(R.id.rd_nhap_xuat);
        rd_nhap = findViewById(R.id.rd_nhap);
        rd_xuat = findViewById(R.id.rd_xuat);

        //set mặc định cho Radio
        rd_nhap_xuat.check(rd_nhap.getId());
        //khi nhan vao lich su giao dich
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
        btn_them_gd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(XuLySpinner() == true)
                {
                    ThemGiaoDich();
                }
            }
        });

        giaoDichController = new QuanLyGiaoDIchController(this);
        ingredientController = new QuanLyNguyenLieuController(this);
        supplierController = new QuanLyNhaCungCapController(this);

        loadNguyenLieu();
        loadNhaCungCap();
        loadDonViDo();

        btn_chon_ngay_gd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgay();
            }
        });

        //lich sư giao dich
        edt_tk_ngaythang = findViewById(R.id.edt_tk_ngay_thang);
        btn_lich_ngaythang_gd = findViewById(R.id.btn_chon_ngaythang_tk);
        btn_tk_giaodich = findViewById(R.id.btn_timkiem_gd);
        lv_giaodich = findViewById(R.id.lv_giaodich);
        allTransactions = giaoDichController.getAllTransactions();
        transactionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allTransactions);
        lv_giaodich.setAdapter(transactionAdapter);

        btn_lich_ngaythang_gd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgayThangTimKiem();
            }
        });
        btn_tk_giaodich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimKiemGiaoDich();
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
    public void ThemGiaoDich()
    {
        // Lấy giá trị từ các trường nhập liệu
        String ingredientName = spn_ten_nguyenlieu.getSelectedItem().toString();
        String unit = spn_donvido.getSelectedItem().toString();
        String supplierName = spn_ten_nha_cc.getSelectedItem().toString();
        String quantityStr = edt_soluong.getText().toString();
        String note = edt_ghichu.getText().toString();
        String transactionDate = edt_ngay_gd.getText().toString();
        String transactionType = "";
        int idRadioCheck = rd_nhap_xuat.getCheckedRadioButtonId(); // Kiểm tra xem là nhập hay xuất
        if(idRadioCheck == R.id.rd_nhap){ transactionType = "Nhập";}
        else{ transactionType = "Xuất";}
        //kiem tra cac truong nhap lieu
        if( XuLyNhap(ingredientName, unit, supplierName, quantityStr, note, transactionDate, transactionType)) {
            // Lấy ID nguyên liệu từ tên
            int ingredientId = LayIDNguyenLieu(ingredientName);
            //Chuyen doi thong tin giao dich sang dung dinh dang
            double quantity = Double.parseDouble(quantityStr);
            if(unit.equals("g")) quantity = quantity/1000;
            long date = convertStringToDate(transactionDate);
            // Thực hiện thêm giao dịch vào cơ sở dữ liệu
            Transaction transaction = new Transaction();
            transaction.setIngredientId(ingredientId);
            transaction.setTransactionDate(date);
            transaction.setTransactionType(transactionType);
            transaction.setQuantity(quantity);
            transaction.setUnit(unit);
            transaction.setNote(note);
            long success = giaoDichController.addTransaction(transaction);
            if (success > 0) {
                Toast.makeText(getApplicationContext(), "Giao dịch đã được thêm", Toast.LENGTH_SHORT).show();
                CapNhatSoLuongNguyenLieu(ingredientId, quantity, transactionType);
            } else {
                Toast.makeText(getApplicationContext(), "Thêm giao dịch không thành công!", Toast.LENGTH_SHORT).show();
                return;
            }

            //hien thi o phan lich su giao dich
            transactionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, giaoDichController.getAllTransactions());
            lv_giaodich.setAdapter(transactionAdapter);
            transactionAdapter.notifyDataSetChanged();
        }

    }
    public boolean KiemTraXuat(String ingredientName, double quantity)
    {
        int ingredientID = LayIDNguyenLieu(ingredientName);
        Ingredient in = new Ingredient();
        listIngredients = ingredientController.getAllIngredient();
        for(int i = 0; i < listIngredients.size(); i++)
        {
            if(ingredientID == listIngredients.get(i).getId())
            {
                in = listIngredients.get(i);
            }
        }
        double soluong = in.getQuantity();
        double ketqua = soluong - quantity;
        if(ketqua < 0) {
            Toast.makeText(this, "Vượt quá số lượng trong kho, số lượng trong kho là: " + soluong, Toast.LENGTH_LONG).show();
            return false;
        }
        else return true;
    }
    public boolean XuLyDonViDo(String ingredientName, String unit)
    {
        Ingredient in = new Ingredient();
        listIngredients = ingredientController.getAllIngredient();
        for(int i = 0; i < listIngredients.size(); i++)
        {
            if(ingredientName.equals(listIngredients.get(i).getName())) in = listIngredients.get(i);
        }
        if(
                ((unit.equals("g") || unit.equals("kg")) && in.getUnit().equals("Lít"))
                        || (unit.equals("lít") &&(in.getUnit().equals("Kg") || in.getUnit().equals("Gram")))
        ){
            Toast.makeText(this, "Đơn vị đo không hợp lệ, vui lòng chọn lại đơn vị đo!", Toast.LENGTH_LONG).show();
            return false;
        }
        else return true;
    }
    //cap nhat so luong nguyen lieu
    public void CapNhatSoLuongNguyenLieu(int ingredientID, double soluong, String transactionType)
    {
        double newQuantity;
        Ingredient in = new Ingredient();
        listIngredients = ingredientController.getAllIngredient();
        for(int i = 0; i < listIngredients.size(); i++)
        {
            if(ingredientID == listIngredients.get(i).getId())
            {
                in = listIngredients.get(i);
            }
        }
        double oldQuantity = in.getQuantity();
        if(transactionType.equals("Xuất")) newQuantity = oldQuantity - soluong;
        else newQuantity = oldQuantity + soluong;
        if(giaoDichController.updateIngredientQuantityById(ingredientID, newQuantity))
        {
            Toast.makeText(this, "Cập nhật số lượng nguyên liệu thành công!", Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(this, "Chưa cập nhật được số lượng nguyên liệu!", Toast.LENGTH_LONG).show();

    }
    //lay id nguyen lieu theo ten
    public int LayIDNguyenLieu(String tenNguyenLieu)
    {
        listIngredients = ingredientController.getAllIngredient();
        Ingredient in = new Ingredient();
        for(int i = 0; i < listIngredients.size(); i++)
        {
            if(tenNguyenLieu.equals(listIngredients.get(i).getName())) in = listIngredients.get(i);
        }
        return in.getId();
    }

    public boolean XuLySpinner() {
        // Kiểm tra nếu controller bị null
        if (supplierController == null || ingredientController == null) {
            Toast.makeText(this, "Lỗi: Không thể truy xuất dữ liệu!", Toast.LENGTH_LONG).show();
            return false;
        }

        // Lấy dữ liệu từ controller
        listSuppliers = supplierController.getAllSuppliers();
        listIngredients = ingredientController.getAllIngredient();

        // Kiểm tra dữ liệu trả về có hợp lệ không
        if (listSuppliers == null || listSuppliers.isEmpty() ||
                listIngredients == null || listIngredients.isEmpty()) {
            Toast.makeText(this, "Chưa có đủ nhà cung cấp và nguyên liệu để thực hiện giao dịch. Vui lòng thêm đủ nhà cung cấp và nguyên liệu!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true; // Dữ liệu hợp lệ, tiếp tục thực hiện giao dịch
    }

    //Xu ly nhap
    public boolean XuLyNhap(String ingredientName, String unit, String supplierName, String quantityStr, String note
            , String transactionDate, String transactionType)
    {
        int idRadioCheck = rd_nhap_xuat.getCheckedRadioButtonId(); // Kiểm tra xem là nhập hay xuất
        if(idRadioCheck == R.id.rd_nhap){ transactionType = "Nhập";}
        else{ transactionType = "Xuất";}
        if(quantityStr.isEmpty() || !isNumeric(quantityStr) || Double.parseDouble(quantityStr) <= 0
                || transactionDate.isEmpty())
        {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin và đúng định dang!", Toast.LENGTH_LONG).show();
            return false;
        }
        double quantity = Double.parseDouble(quantityStr);
        //kiem tra so luong xuat
        if(transactionType.equals("Xuất"))
        {
            if(KiemTraXuat(ingredientName, quantity) == false) return false;
        }
        if(XuLyDonViDo(ingredientName, unit) == false) return false;
        // Chuyển đổi thông tin giao dịch sang đúng định dạng
        long date = convertStringToDate(transactionDate); // Chuyển đổi ngày thành kiểu long
        // Kiểm tra ngày hợp lệ (có thể tùy chỉnh thêm phần này để kiểm tra đúng định dạng ngày)
        if (date == 0) {
            Toast.makeText(getApplicationContext(), "Ngày giao dịch không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }

    public void ChonNgay()
    {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String ngayGiaoDich = year1 + "-" + (month1 + 1) + "-" + dayOfMonth;
            edt_ngay_gd.setText(ngayGiaoDich);
        }, year, month, day);
        datePickerDialog.show();
    }

    public void loadDonViDo() {
        String[] donViDo = {"kg" ,"g", "lít"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, donViDo);
        spn_donvido.setAdapter(adapter);
        spn_donvido.setSelection(0);
    }

    private void loadNhaCungCap() {
        listTenNhanCC = giaoDichController.getAllSupplierNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listTenNhanCC);
        spn_ten_nha_cc.setAdapter(adapter);
        if(listTenNhanCC != null) spn_ten_nha_cc.setSelection(0);
    }
    private void loadNguyenLieu() {
        listTenNguyenLieu = giaoDichController.getAllIngredientNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listTenNguyenLieu);
        spn_ten_nguyenlieu.setAdapter(adapter);
        if(listTenNhanCC != null) spn_ten_nguyenlieu.setSelection(0);
    }

    // Hàm kiểm tra xem chuỗi có phải là số không
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Hàm chuyển đổi chuỗi ngày sang long
    private long convertStringToDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = sdf.parse(dateString);
            if (date != null) {
                return date.getTime(); // Trả về thời gian dưới dạng long
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu ngày không hợp lệ
    }
    // Phan lich su giao dich
// Hàm chọn ngày tháng và cập nhật vào EditText
    private void ChonNgayThangTimKiem() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        // Tạo DatePickerDialog để chọn ngày
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Khi người dùng chọn ngày, hiển thị trong EditText
                    edt_tk_ngaythang.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
    // Hàm tìm kiếm giao dịch theo ngày tháng
    private void TimKiemGiaoDich() {
        String selectedDate = edt_tk_ngaythang.getText().toString().trim();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Kiểm tra nếu ngày tháng không trống, lọc giao dịch theo ngày
        if (!selectedDate.isEmpty()) {
            transactionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, giaoDichController.searchTransactionsByDate(selectedDate));
            lv_giaodich.setAdapter(transactionAdapter);

        } else {
            // Nếu ngày tháng trống, hiển thị tất cả giao dịch
            transactionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allTransactions);
            lv_giaodich.setAdapter(transactionAdapter);
        }
    }


}