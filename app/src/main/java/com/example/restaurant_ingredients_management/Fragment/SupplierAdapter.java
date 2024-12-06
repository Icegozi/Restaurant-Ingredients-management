package com.example.restaurant_ingredients_management.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.restaurant_ingredients_management.Model.Supplier;
import com.example.restaurant_ingredients_management.R;

import java.util.List;

public class SupplierAdapter extends ArrayAdapter<Supplier> {
    private Context context;
    private List<Supplier> suppliers;

    public SupplierAdapter(Context context, List<Supplier> suppliers) {
        super(context, 0, suppliers);
        this.context = context;
        this.suppliers = suppliers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.supplier_item, parent, false);
        }

        Supplier supplier = suppliers.get(position);

        TextView tvSupplierName = convertView.findViewById(R.id.tvSupplierName);
        TextView tvSupplierContact = convertView.findViewById(R.id.tvSupplierContact);
        TextView tvSupplierAddress = convertView.findViewById(R.id.tvSupplierAddress);

        tvSupplierName.setText(supplier.getName());
        tvSupplierContact.setText(supplier.getContactInfo());
        tvSupplierAddress.setText(supplier.getAddress());

        return convertView;
    }
}
