package com.example.restaurant_ingredients_management.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.restaurant_ingredients_management.Controller.QuanLyNhaCungCapController;
import com.example.restaurant_ingredients_management.Fragment.SupplierAdapter;
import com.example.restaurant_ingredients_management.MainActivity;
import com.example.restaurant_ingredients_management.Model.Supplier;
import com.example.restaurant_ingredients_management.R;

import java.util.ArrayList;

public class NhaCungCap extends AppCompatActivity {
    EditText edt_ncc, edt_tt_lienhe, edt_dia_chi;
    ListView lv_ncc;
    Button btn_them, btn_sua, btn_xoa, btn_timkiem;
    ArrayAdapter<Supplier> supplierAdapter;
    ArrayList<Supplier> supplierList = new ArrayList<Supplier>();
    int n; //layvitrilistview?
    int id_ncc; //layvitrilistview?
    private QuanLyNhaCungCapController supplierController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nha_cung_cap);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setTitle("Nhà cung cấp");
        edt_ncc = findViewById(R.id.edt_ten_ncc);
        edt_tt_lienhe = findViewById(R.id.edt_tt_lienhe);
        edt_dia_chi = findViewById(R.id.edt_dia_chi);
        btn_sua = findViewById(R.id.btn_sua_ncc);
        btn_them = findViewById(R.id.btn_them_ncc);
        btn_xoa = findViewById(R.id.btn_xoa_ncc);
        btn_timkiem = findViewById(R.id.btn_timkiem_ncc);
        lv_ncc = findViewById(R.id.lv_ncc);

        //Khởi tạo Controller và Adapter
        supplierController = new QuanLyNhaCungCapController(this);
        supplierAdapter = new SupplierAdapter(this,supplierController.getAllSuppliers());
        lv_ncc.setAdapter(supplierAdapter);

        //khi chon 1 listview
        lv_ncc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChonListView(i);
                n = i;
            }
        });

        //khi nhan nut them
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenNcc = edt_ncc.getText().toString().trim();
                String ttLienhe = edt_tt_lienhe.getText().toString().trim();
                String diaChi  = edt_dia_chi.getText().toString().trim();
                if(XuLyNhap() == true && CheckSupplier(tenNcc) == true)
                {
                    NewSupplier(tenNcc, ttLienhe, diaChi);
                }
            }
        });

        //khi nhan nut sua
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( XuLyNhap() == true)
                {
                    XuLySua(n);
                }
            }
        });
        //khi an nut xoa
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XulyXoa();
            }
        });
        //khi an nut tim kiem
        btn_timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XuLyTimKiem();
            }
        });
    }
    public void XuLyTimKiem() {
        String keyword = edt_ncc.getText().toString().trim();
        ArrayList<Supplier> results = supplierController.searchSuppliers(keyword);
        if (keyword.isEmpty()) {
            supplierAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, supplierController.getAllSuppliers());
            lv_ncc.setAdapter(supplierAdapter);
            supplierAdapter.notifyDataSetChanged();
        } else {
            if (results.isEmpty()) {
                Toast.makeText(this, "Không tìm thấy nhà cung cấp nào!", Toast.LENGTH_SHORT).show();
            } else {
                supplierAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
                lv_ncc.setAdapter(supplierAdapter);
                supplierAdapter.notifyDataSetChanged();
            }
        }
    }
    private void XulyXoa() {
        if (id_ncc == -1) {
            Toast.makeText(this, "Vui lòng chọn nhà cung cấp cần xóa!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hiển thị hộp thoại xác nhận
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa nhà cung cấp này không?");

        // Nút xác nhận xóa
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Thực hiện xóa nhà cung cấp
                int result = supplierController.deleteSupplier(id_ncc);
                if (result > 0) {
                    Toast.makeText(NhaCungCap.this, "Xóa nhà cung cấp thành công!", Toast.LENGTH_SHORT).show();
                    capNhatDanhSachNhaCungCap();
                    clearInputFields();
                } else {
                    Toast.makeText(NhaCungCap.this, "Xóa nhà cung cấp thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Nút hủy xóa
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Đóng hộp thoại, không thực hiện xóa
                dialog.dismiss();
            }
        });

        // Hiển thị hộp thoại
        builder.create().show();
    }
    //tao nha cung cap, luu vao database va them vao listView
    public void ChonListView(int i )
    {
        ArrayList<Supplier> allSuppliers = supplierController.getAllSuppliers();
        Supplier selectedSupplier = allSuppliers.get(i);
        if (selectedSupplier != null) {
            id_ncc = selectedSupplier.getId();
            selectedSupplier.setId(selectedSupplier.getId());
            edt_ncc.setText(selectedSupplier.getName());
            edt_tt_lienhe.setText(selectedSupplier.getContactInfo());
            edt_dia_chi.setText(selectedSupplier.getAddress());
        }
    }

    public void XuLySua(int k)
    {
        String name = edt_ncc.getText().toString().trim();
        String contactInfo = edt_tt_lienhe.getText().toString().trim();
        String address = edt_dia_chi.getText().toString().trim();

        Supplier updatedSupplier = new Supplier(id_ncc, name, contactInfo, address);
        int result = supplierController.updateSupplier(updatedSupplier);
        if (result > 0) {
            Toast.makeText(this, "Sửa nhà cung cấp thành công!", Toast.LENGTH_SHORT).show();
            capNhatDanhSachNhaCungCap();
            clearInputFields();
        } else {
            Toast.makeText(this, "Sửa nhà cung cấp thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
    public void NewSupplier(String tenNcc, String ttLienhe, String diaChi)
    {
        Supplier sl = new Supplier();
        sl.setName(tenNcc);
        sl.setAddress(diaChi);
        sl.setContactInfo(ttLienhe);
        long result = supplierController.addSupplier(sl);
        //cai nay tra ve id
        if (result > 0) {
            Toast.makeText(this, "Thêm nhà cung cấp thành công!", Toast.LENGTH_SHORT).show();
            capNhatDanhSachNhaCungCap();
            clearInputFields();
        } else {
            Toast.makeText(this, "Thêm nhà cung cấp thất bại!", Toast.LENGTH_SHORT).show();
        }

    }
    private void capNhatDanhSachNhaCungCap() {
        supplierAdapter = new SupplierAdapter(this,supplierController.getAllSuppliers());
        lv_ncc.setAdapter(supplierAdapter);
        // Cập nhật ListView
        supplierAdapter.notifyDataSetChanged();
    }
    //xu ly nhap
    public boolean XuLyNhap()
    {
        String tenNcc = edt_ncc.getText().toString().trim();
        String ttLienhe = edt_tt_lienhe.getText().toString().trim();
        String diaChi  = edt_dia_chi.getText().toString().trim();
        if(tenNcc.isEmpty() || ttLienhe.isEmpty() || diaChi.isEmpty())
        {
            Toast.makeText(this, "Không được để trống trường nào!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
    private void clearInputFields() {
        edt_ncc.setText("");
        edt_tt_lienhe.setText("");
        edt_dia_chi.setText("");
        edt_ncc.requestFocus(); // Đưa con trỏ về trường đầu tiên
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
            Intent intent = new Intent(NhaCungCap.this, GiaoDich.class);
            startActivity(intent);
            finish();
        }
        if(id==R.id.opNguyenLieu){
            Intent intent = new Intent(NhaCungCap.this, NguyenLieu.class);
            startActivity(intent);
            finish();
        }
        if(id==R.id.opNhaCungCap){
            Intent intent = new Intent(NhaCungCap.this, NhaCungCap.class);
            startActivity(intent);
            finish();
        }
        if(id==R.id.opThongBao){
            Intent intent = new Intent(NhaCungCap.this, ThongBao.class);
            startActivity(intent);
            finish();
        }
        if(id==R.id.opTrangChu){
            Intent intent = new Intent(NhaCungCap.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (id==R.id.opHinhAnh){
            Intent intent = new Intent(NhaCungCap.this, HinhAnh.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    //Kiem tra nha cung cap có bi trùng không
    public boolean CheckSupplier(String Name)
    {
        ArrayList <Supplier> listSup = null;
        listSup = supplierController.getAllSuppliers();
        int dem = 0;
        for(int i = 0; i < listSup.size(); i++)
        {
            if(Name.equals(listSup.get(i).getName()))
            {
                Toast.makeText(this, "Trùng tên nhà cung cấp, vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                dem++;
            }
        }
        if(dem > 0) return false;
        else return true;
    }
}