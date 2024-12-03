package com.example.restaurant_ingredients_management.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.restaurant_ingredients_management.Model.Ingredient;
import com.example.restaurant_ingredients_management.R;
import java.util.List;

public class IngredientAdapter extends ArrayAdapter<Ingredient>{
    private Context context;
    private List<Ingredient> ingredients;

    public IngredientAdapter(Context context, List<Ingredient> ingredients) {
        super(context, R.layout.list_item_ingredient, ingredients);
        this.context = context; this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_ingredient, parent, false);
        }

        Ingredient ingredient = ingredients.get(position);
        ImageView imageView = convertView.findViewById(R.id.imageViewIngredient);
        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewQuantity = convertView.findViewById(R.id.textViewQuantity);
        TextView textViewExpirationDate = convertView.findViewById(R.id.textViewExpirationDate);

        if (ingredient.getImageData() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(ingredient.getImageData(), 0, ingredient.getImageData().length);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.bo_bit_tet); // Đặt ảnh mặc định nếu không có ảnh
        }
        textViewName.setText(ingredient.getName());
        textViewQuantity.setText("Số lượng: " + ingredient.getQuantity() + " " + ingredient.getUnit());
        textViewExpirationDate.setText("Hạn sử dụng: " + ingredient.convertLongToDate(ingredient.getExpirationDate()));
        return convertView;
    }
}
