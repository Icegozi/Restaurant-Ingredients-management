package com.example.restaurant_ingredients_management.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.restaurant_ingredients_management.Model.Ingredient;
import com.example.restaurant_ingredients_management.Model.IngredientSupplier;
import com.example.restaurant_ingredients_management.Model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class QuanLyNguyenLieuDBO {
    private SQLiteDatabase db;
    private CreateDatabase dbHelper;

    // Constructor để khởi tạo dbHelper
    public QuanLyNguyenLieuDBO(Context context) {
        dbHelper = new CreateDatabase(context); // Khởi tạo dbHelper với Context
    }

    // Mở kết nối cơ sở dữ liệu
    public void open() throws SQLException {
        if (dbHelper != null) {
            db = dbHelper.getWritableDatabase();
        } else {
            throw new SQLException("Database helper is null");
        }
    }

    // Đóng kết nối cơ sở dữ liệu
    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    // Thêm một nguyên liệu mới
    public long insertIngredient(Ingredient ingredient) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_INGREDIENT_NAME, ingredient.getName());
        values.put(CreateDatabase.COLUMN_INGREDIENT_IMAGE, ingredient.getImageData());
        values.put(CreateDatabase.COLUMN_INGREDIENT_EXPIRATION_DATE, ingredient.getExpirationDate());
        values.put(CreateDatabase.COLUMN_INGREDIENT_IS_LOW_STOCK, ingredient.isLowStock() ? 1 : 0);
        values.put(CreateDatabase.COLUMN_INGREDIENT_QUANTITY, ingredient.getQuantity());
        values.put(CreateDatabase.COLUMN_INGREDIENT_UNIT, ingredient.getUnit());
        values.put(CreateDatabase.COLUMN_INGREDIENT_LAST_UPDATED, ingredient.getLastUpdated());
        return db.insert(CreateDatabase.TABLE_INGREDIENTS, null, values); // Trả về ID dòng vừa thêm
    }

    // Lấy tất cả các nguyên liệu
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        Cursor cursor = db.query(CreateDatabase.TABLE_INGREDIENTS, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_ID)));
                ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_NAME)));
                ingredient.setImageData(cursor.getBlob(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_IMAGE)));
                ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_QUANTITY)));
                ingredient.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_UNIT)));
                ingredient.setExpirationDate(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_EXPIRATION_DATE)));
                ingredient.setLowStock(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_IS_LOW_STOCK)) == 1);
                ingredient.setLastUpdated(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_LAST_UPDATED)));
                ingredients.add(ingredient);
            }
            cursor.close();
        }
        return ingredients;
    }

    // Lấy một nguyên liệu theo id
    public Ingredient getIngredientById(int id) {
        Ingredient ingredient = null;
        String selection = CreateDatabase.COLUMN_INGREDIENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(CreateDatabase.TABLE_INGREDIENTS, null, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            ingredient = new Ingredient();
            ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_ID)));
            ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_NAME)));
            ingredient.setImageData(cursor.getBlob(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_IMAGE)));
            ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_QUANTITY)));
            ingredient.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_UNIT)));
            ingredient.setExpirationDate(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_EXPIRATION_DATE)));
            ingredient.setLowStock(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_IS_LOW_STOCK)) == 1);
            ingredient.setLastUpdated(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_LAST_UPDATED)));
            cursor.close();
        }
        return ingredient;
    }

    // Cập nhật một nguyên liệu
    public int updateIngredient(Ingredient ingredient) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_INGREDIENT_NAME, ingredient.getName());
        values.put(CreateDatabase.COLUMN_INGREDIENT_IMAGE, ingredient.getImageData());
        values.put(CreateDatabase.COLUMN_INGREDIENT_EXPIRATION_DATE, ingredient.getExpirationDate());
        values.put(CreateDatabase.COLUMN_INGREDIENT_IS_LOW_STOCK, ingredient.isLowStock() ? 1 : 0);
        values.put(CreateDatabase.COLUMN_INGREDIENT_QUANTITY, ingredient.getQuantity());
        values.put(CreateDatabase.COLUMN_INGREDIENT_UNIT, ingredient.getUnit());
        values.put(CreateDatabase.COLUMN_INGREDIENT_LAST_UPDATED, ingredient.getLastUpdated());

        String whereClause = CreateDatabase.COLUMN_INGREDIENT_ID + " = ?";
        String[] whereArgs = {String.valueOf(ingredient.getId())};
        return db.update(CreateDatabase.TABLE_INGREDIENTS, values, whereClause, whereArgs);
    }

    // Cập nhật số lượng của nguyên liệu theo id
    public int updateIngredientQuantity(int ingredientId, double newQuantity) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_INGREDIENT_QUANTITY, newQuantity);
        //lấy thời gian hiên tại của hệ thống
        long currentTime = System.currentTimeMillis();
        values.put(CreateDatabase.COLUMN_INGREDIENT_LAST_UPDATED, currentTime);

        String whereClause = CreateDatabase.COLUMN_INGREDIENT_ID + " = ?";
        String[] whereArgs = {String.valueOf(ingredientId)};

        return db.update(CreateDatabase.TABLE_INGREDIENTS, values, whereClause, whereArgs);
    }

    // Xóa một nguyên liệu
    public int deleteIngredient(int id) {
        String whereClause = CreateDatabase.COLUMN_INGREDIENT_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        return db.delete(CreateDatabase.TABLE_INGREDIENTS, whereClause, whereArgs);
    }

    //Thêm liên kết giá tiền giữa nhà cung cấp và nguyên liệu
    public long insertIngredientSupplier(IngredientSupplier ingredientSupplier) {
        ContentValues values = new ContentValues();
        values.put("ingredientId", ingredientSupplier.getIngredientId());  // Đảm bảo có giá trị
        values.put("supplierId", ingredientSupplier.getSupplierId());  // Đảm bảo có giá trị
        values.put("pricePerUnit", ingredientSupplier.getPricePerUnit());
        values.put("supplyDate", ingredientSupplier.getSupplyDate());

        Log.d("DEBUG", "Values to insert: " + values.toString());  // Log values trước khi chèn

        return db.insert(CreateDatabase.TABLE_INGREDIENT_SUPPLIERS, null, values);
    }


    // Phương thức lấy tất cả các bản ghi từ bảng IngredientSupplier
    public List<IngredientSupplier> getAllIngredientSuppliers() {
        List<IngredientSupplier> ingredientSuppliers = new ArrayList<>();
        Cursor cursor = db.query(CreateDatabase.TABLE_INGREDIENT_SUPPLIERS, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                IngredientSupplier ingredientSupplier = new IngredientSupplier();
                ingredientSupplier.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_ID)));
                ingredientSupplier.setIngredientId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_INGREDIENT_ID)));
                ingredientSupplier.setSupplierId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_SUPPLIER_ID)));
                ingredientSupplier.setPricePerUnit(cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_PRICE_PER_UNIT)));
                ingredientSupplier.setSupplyDate(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_SUPPLY_DATE)));
                ingredientSuppliers.add(ingredientSupplier);
            } cursor.close();
        }
        return ingredientSuppliers;
    }

    // Lấy danh sách nhà cung cấp cho một nguyên liệu
//    public List<IngredientSupplier> getSuppliersByIngredientId(int ingredientId) {
//        List<IngredientSupplier> suppliers = new ArrayList<>();
//        String selection = CreateDatabase.COLUMN_INGREDIENT_ID + " = ?";
//        String[] selectionArgs = {String.valueOf(ingredientId)};
//        Cursor cursor = db.query(CreateDatabase.TABLE_INGREDIENT_SUPPLIERS, null, selection, selectionArgs, null, null, null);
//
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                IngredientSupplier supplier = new IngredientSupplier(
//                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
//                        cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_ID)),
//                        cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_ID)),
//                        cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_PRICE_PER_UNIT)),
//                        cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_SUPPLY_DATE))
//                );
//                suppliers.add(supplier);
//            }
//            cursor.close();
//        }
//        return suppliers;
//    }


    public int updateIngredientPrice(int ingredientId, int supplierId, double newPrice) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_PRICE_PER_UNIT, newPrice);

        String whereClause = CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_INGREDIENT_ID + " = ? AND "
                + CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_SUPPLIER_ID + " = ?";
        String[] whereArgs = {String.valueOf(ingredientId), String.valueOf(supplierId)};

        return db.update(CreateDatabase.TABLE_INGREDIENT_SUPPLIERS, values, whereClause, whereArgs);
    }

    // Lấy tất cả các nhà cung cấp
    public ArrayList<Supplier> getAllSuppliers() {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        Cursor cursor = db.query(CreateDatabase.TABLE_SUPPLIERS, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Supplier supplier = new Supplier();
                supplier.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_ID)));
                supplier.setName(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_NAME)));
                supplier.setContactInfo(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_CONTACT_INFO)));
                supplier.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_ADDRESS)));
                suppliers.add(supplier);
            }
            cursor.close();
        }
        return suppliers;
    }

    // Lấy một nhà cung cấp theo id
    public Supplier getSupplierById(int id) {
        Supplier supplier = null;
        String selection = CreateDatabase.COLUMN_SUPPLIER_ID + "= ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = db.query(CreateDatabase.TABLE_SUPPLIERS, null, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            supplier = new Supplier();
            supplier.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_ID)));
            supplier.setName(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_NAME)));
            supplier.setContactInfo(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_CONTACT_INFO)));
            supplier.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_ADDRESS)));
            cursor.close();
        }
        return supplier;
    }

    // Hàm cập nhật ảnh cho nguyên liệu dựa trên ID
    public int saveIngredientImage(int ingredientId, byte[] imageData) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_INGREDIENT_IMAGE, imageData);

        String whereClause = CreateDatabase.COLUMN_INGREDIENT_ID + " = ?";
        String[] whereArgs = {String.valueOf(ingredientId)};

        try {
            return db.update(CreateDatabase.TABLE_INGREDIENTS, values, whereClause, whereArgs);
        } catch (SQLException e) {
            Log.e("Database Error", "Lỗi khi lưu ảnh nguyên liệu: ", e);
            return -1; // Trả về -1 nếu lỗi xảy ra
        }
    }

    // Hàm lấy ảnh của nguyên liệu dựa trên ID
    public byte[] getIngredientImage(int ingredientId) {
        byte[] imageData = null; // Biến để lưu dữ liệu ảnh
        String selection = CreateDatabase.COLUMN_INGREDIENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(ingredientId)};

        Cursor cursor = null;
        try {
            cursor = db.query(
                    CreateDatabase.TABLE_INGREDIENTS, // Tên bảng
                    new String[]{CreateDatabase.COLUMN_INGREDIENT_IMAGE}, // Chỉ lấy cột ảnh
                    selection, // Điều kiện
                    selectionArgs, // Giá trị điều kiện
                    null, // Không group by
                    null, // Không having
                    null // Không order by
            );

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy dữ liệu ảnh từ cột tương ứng
                imageData = cursor.getBlob(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_IMAGE));
            }
        } catch (SQLException e) {
            Log.e("Database Error", "Lỗi khi lấy ảnh nguyên liệu: ", e);
        } finally {
            if (cursor != null) {
                cursor.close(); // Đóng Cursor để tránh rò rỉ bộ nhớ
            }
        }
        return imageData; // Trả về dữ liệu ảnh (hoặc null nếu không tìm thấy)
    }

    public List<Supplier> getSuppliersByIngredientId(int ingredientId) {
        List<Supplier> suppliers = new ArrayList<>();
        Cursor cursor = null;

        try {
            open();

            String sql = "SELECT " + CreateDatabase.TABLE_SUPPLIERS + "." + CreateDatabase.COLUMN_SUPPLIER_ID + ", " +
                    CreateDatabase.TABLE_SUPPLIERS + "." + CreateDatabase.COLUMN_SUPPLIER_NAME + ", " +
                    CreateDatabase.TABLE_SUPPLIERS + "." + CreateDatabase.COLUMN_SUPPLIER_CONTACT_INFO + ", " +
                    CreateDatabase.TABLE_SUPPLIERS + "." + CreateDatabase.COLUMN_SUPPLIER_ADDRESS +
                    " FROM " + CreateDatabase.TABLE_SUPPLIERS +
                    " JOIN " + CreateDatabase.TABLE_INGREDIENT_SUPPLIERS +
                    " ON " + CreateDatabase.TABLE_SUPPLIERS + "." + CreateDatabase.COLUMN_SUPPLIER_ID + " = " +
                    CreateDatabase.TABLE_INGREDIENT_SUPPLIERS + "." + CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_SUPPLIER_ID +
                    " WHERE " + CreateDatabase.TABLE_INGREDIENT_SUPPLIERS + "." + CreateDatabase.COLUMN_INGREDIENT_SUPPLIER_INGREDIENT_ID + " = ?";

            cursor = db.rawQuery(sql, new String[]{String.valueOf(ingredientId)});

            // Kiểm tra tên cột trong cursor
            if (cursor != null && cursor.moveToFirst()) {
                String[] columnNames = cursor.getColumnNames(); // Lấy danh sách tên cột trong cursor
                for (String columnName : columnNames) {
                    Log.d("Cursor Column", "Column Name: " + columnName); // In ra các tên cột
                }

                // Đọc kết quả từ cursor và thêm vào danh sách
                do {
                    Supplier supplier = new Supplier();
                    int supplierIdIndex = cursor.getColumnIndex(CreateDatabase.COLUMN_SUPPLIER_ID);
                    if (supplierIdIndex != -1) {
                        supplier.setId(cursor.getInt(supplierIdIndex));
                    }

                    int nameIndex = cursor.getColumnIndex(CreateDatabase.COLUMN_SUPPLIER_NAME);
                    if (nameIndex != -1) {
                        supplier.setName(cursor.getString(nameIndex));
                    }

                    int contactInfoIndex = cursor.getColumnIndex(CreateDatabase.COLUMN_SUPPLIER_CONTACT_INFO);
                    if (contactInfoIndex != -1) {
                        supplier.setContactInfo(cursor.getString(contactInfoIndex));
                    }

                    int addressIndex = cursor.getColumnIndex(CreateDatabase.COLUMN_SUPPLIER_ADDRESS);
                    if (addressIndex != -1) {
                        supplier.setAddress(cursor.getString(addressIndex));
                    }

                    suppliers.add(supplier);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            close();
        }

        return suppliers;
    }




}
