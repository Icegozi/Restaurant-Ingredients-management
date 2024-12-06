package com.example.restaurant_ingredients_management.Fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurant_ingredients_management.Controller.QuanLyGiaoDIchController;
import com.example.restaurant_ingredients_management.Model.Ingredient;
import com.example.restaurant_ingredients_management.Model.IngredientSupplier;
import com.example.restaurant_ingredients_management.Model.Transaction;
import com.example.restaurant_ingredients_management.R;
import com.example.restaurant_ingredients_management.Database.QuanLyNhaCungCap_NguyenLieuDBO;
import com.example.restaurant_ingredients_management.Database.QuanLyNguyenLieuDBO;
import com.example.restaurant_ingredients_management.Controller.QuanLyNhaCC_NguyenLieuController;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

private Context context;
private QuanLyNhaCC_NguyenLieuController ingredientSupplierController;
    private ArrayList<Transaction> transactions;
    private List<Ingredient> ingredients;
    private Double tongTien;
    private QuanLyGiaoDIchController qlgd;

    public TransactionAdapter(Context context, ArrayList<Transaction> transactions, List<Ingredient> ingredients) {
        super(context, 0, transactions); // Truyền context và danh sách giao dịch
        this.context = context;
        this.transactions = transactions;
        this.ingredients = ingredients;
        qlgd = new QuanLyGiaoDIchController(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_giao_dich_adapter, parent, false);
        }
        convertView.setBackgroundResource(R.drawable.border_white);
        // Lấy đối tượng giao dịch và nguyên liệu tại vị trí hiện tại
        Transaction transaction = transactions.get(position);
        Ingredient ingredient = findIngredientById(transaction.getIngredientId());
        // Kiểm tra giá trị của ingredientId và supplierId
        Log.d("TransactionAdapter", "ingredientId: " + transaction.getIngredientId() + ", supplierId: " + transaction.getSupplierId());


        // Ánh xạ các views từ layout
        TextView tvIngredientName = convertView.findViewById(R.id.txtIngredientName);
        TextView tvTotalPrice = convertView.findViewById(R.id.txtTotalPrice);
        ImageView ivIconDollar = convertView.findViewById(R.id.iconDollar);
        TextView tvTransactionType = convertView.findViewById(R.id.txtTransactionType);
        TextView tvQuantity = convertView.findViewById(R.id.txtQuantity);
        TextView tvUnit = convertView.findViewById(R.id.txtUnit);
        TextView tvNote = convertView.findViewById(R.id.txtNote);
        TextView tvDate = convertView.findViewById(R.id.txtDate);

        tongTien = qlgd.layGiaCaNguyenLieu(transaction.getSupplierId(),transaction.getIngredientId())*transaction.getQuantity();
        // Hiển thị dữ liệu vào các TextView
        String TransName = ingredient != null ? ingredient.getName() : "Không xác định";
        String note = transaction.getNote().isEmpty()? "không có ghi chú" : transaction.getNote();
        String Unit;
        if(transaction.getUnit().equalsIgnoreCase("Kilogram")||transaction.getUnit().equalsIgnoreCase("Gram")){
            Unit = "Kg";
        }else{
            Unit = "Lít";
        }
        tvIngredientName.setText(TransName);
        tvTotalPrice.setText(String.valueOf(tongTien));
        tvTransactionType.setText("Đã "+transaction.getTransactionType() + " ");
        tvQuantity.setText(String.valueOf(transaction.getQuantity()) + " ");
        tvUnit.setText(Unit + " "+TransName);
        tvNote.setText("Ghi chú: " + note);
        tvDate.setText(transaction.formatDate(transaction.getTransactionDate()));

        // Trả về convertView đã được gán dữ liệu
        return convertView;
    }

    private Ingredient findIngredientById(int ingredientId) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getId() == ingredientId) {
                return ingredient;
            }
        }
        return null; // Trả về null nếu không tìm thấy nguyên liệu
    }


}