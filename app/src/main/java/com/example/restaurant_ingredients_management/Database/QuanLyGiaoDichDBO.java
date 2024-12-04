package com.example.restaurant_ingredients_management.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.restaurant_ingredients_management.Model.Transaction;
import com.example.restaurant_ingredients_management.View.GiaoDich;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.example.restaurant_ingredients_management.View.GiaoDich;

public class QuanLyGiaoDichDBO {
    private SQLiteDatabase db;
    private CreateDatabase dbHelper;

    public QuanLyGiaoDichDBO(Context context) {
        dbHelper = new CreateDatabase(context); // Khởi tạo dbHelper
    }

    // Mở kết nối cơ sở dữ liệu
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    // Đóng kết nối cơ sở dữ liệu
    public void close() {
        dbHelper.close();
    }

    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        open();
        if (db == null || !db.isOpen()) {
            open();
            throw new IllegalStateException("Database is closed or null!");
        }

        Cursor cursor = db.query(CreateDatabase.TABLE_TRANSACTIONS, null, null, null, null, null, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_ID)));
                    transaction.setIngredientId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_INGREDIENT_ID)));
                    transaction.setTransactionDate(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_DATE)));
                    transaction.setTransactionType(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_TYPE)));
                    transaction.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_QUANTITY)));
                    transaction.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_UNIT)));
                    transaction.setNote(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_NOTE)));
                    transactions.add(transaction);
                }
            } finally {
                cursor.close(); // Đảm bảo đóng cursor ở đây
            }
        }

        return transactions;
    }

    // Lấy một giao dịch theo id
    public Transaction getTransactionById(int id) {
        Transaction transaction = null;
        String selection = CreateDatabase.COLUMN_TRANSACTION_ID + "= ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = db.query(CreateDatabase.TABLE_TRANSACTIONS, null, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            transaction = new Transaction();
            transaction.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_ID)));
            transaction.setIngredientId(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_INGREDIENT_ID)));
            transaction.setTransactionDate(cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_DATE)));
            transaction.setTransactionType(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_TYPE)));
            transaction.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_QUANTITY)));
            transaction.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_UNIT)));
            transaction.setNote(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_NOTE)));
            cursor.close();
        }
        return transaction;
    }

    // Cập nhật thông tin giao dịch
    public int updateTransaction(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_TRANSACTION_INGREDIENT_ID, transaction.getIngredientId());
        values.put(CreateDatabase.COLUMN_TRANSACTION_DATE, transaction.getTransactionDate());
        values.put(CreateDatabase.COLUMN_TRANSACTION_TYPE, transaction.getTransactionType());
        values.put(CreateDatabase.COLUMN_TRANSACTION_QUANTITY, transaction.getQuantity());
        values.put(CreateDatabase.COLUMN_TRANSACTION_UNIT, transaction.getUnit());
        values.put(CreateDatabase.COLUMN_TRANSACTION_NOTE, transaction.getNote());

        String whereClause = CreateDatabase.COLUMN_TRANSACTION_ID + " = ?";
        String[] whereArgs = { String.valueOf(transaction.getId()) };
        return db.update(CreateDatabase.TABLE_TRANSACTIONS, values, whereClause, whereArgs);
    }

    // Xóa một giao dịch
    public int deleteTransaction(int id) {
        String whereClause = CreateDatabase.COLUMN_TRANSACTION_ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };
        return db.delete(CreateDatabase.TABLE_TRANSACTIONS, whereClause, whereArgs);
    }
    // Lấy danh sách tất cả tên nguyên liệu
    public ArrayList<String> getAllIngredientNames() {
        ArrayList<String> ingredientNames = new ArrayList<>();
        Cursor cursor = db.query(CreateDatabase.TABLE_INGREDIENTS, new String[]{CreateDatabase.COLUMN_INGREDIENT_NAME}, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_INGREDIENT_NAME));
                ingredientNames.add(name);
            }
            cursor.close();
        }
        return ingredientNames;
    }

    // Lấy danh sách tất cả tên nhà cung cấp
    public ArrayList<String> getAllSupplierNames() {
        ArrayList<String> supplierNames = new ArrayList<>();
        Cursor cursor = db.query(CreateDatabase.TABLE_SUPPLIERS, new String[]{CreateDatabase.COLUMN_SUPPLIER_NAME}, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_SUPPLIER_NAME));
                supplierNames.add(name);
            }
            cursor.close();
        }
        return supplierNames;
    }

    // Lấy ID của nguyên liệu từ tên nguyên liệu
    public int layIdNguyenLieu(String ingredientName) {
        // Truy vấn bảng INGREDIENTS để lấy ID của nguyên liệu
        Cursor cursor = db.query(CreateDatabase.TABLE_INGREDIENTS,
                new String[]{CreateDatabase.COLUMN_INGREDIENT_ID}, // Lấy cột ID
                CreateDatabase.COLUMN_INGREDIENT_NAME + "=?", // Điều kiện tìm kiếm theo tên nguyên liệu
                new String[]{ingredientName}, // Tên nguyên liệu cần tìm
                null, null, null); // Không nhóm và không sắp xếp

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(0); // Lấy ID nguyên liệu từ kết quả truy vấn
            cursor.close(); // Đóng con trỏ sau khi lấy xong dữ liệu
            return id; // Trả về ID tìm được
        }
        return -1; // Nếu không tìm thấy nguyên liệu, trả về -1
    }
    // Cập nhật số lượng nguyên liệu trong kho
    public void capNhatSoLuongNguyenLieu(int ingredientId, double quantity, String transactionType) {
        // Truy vấn để lấy số lượng nguyên liệu hiện tại
        Cursor cursor = db.query(CreateDatabase.TABLE_INGREDIENTS, new String[]{CreateDatabase.COLUMN_INGREDIENT_QUANTITY, CreateDatabase.COLUMN_INGREDIENT_NAME},
                CreateDatabase.COLUMN_INGREDIENT_ID + "=?", new String[]{String.valueOf(ingredientId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            double currentQuantity = cursor.getDouble(0); // Số lượng hiện tại của nguyên liệu
            String ingredientName = cursor.getString(1); // Tên nguyên liệu
            cursor.close();

            // Tính số lượng mới dựa trên loại giao dịch (input hoặc output)
            double newQuantity = transactionType.equals("input") ? currentQuantity + quantity : currentQuantity - quantity;

            // Cập nhật lại số lượng trong bảng
            ContentValues values = new ContentValues();
            values.put(CreateDatabase.COLUMN_INGREDIENT_QUANTITY, newQuantity);
            db.update(CreateDatabase.TABLE_INGREDIENTS, values, CreateDatabase.COLUMN_INGREDIENT_ID + "=?", new String[]{String.valueOf(ingredientId)});

            // Hiển thị thông báo
            System.out.println("Số lượng của nguyên liệu " + ingredientName + " còn " + currentQuantity + " +(-) " + quantity + " = " + newQuantity);
        }
    }
    //tim kiem giao dich theo ngay
    public ArrayList<Transaction> searchTransactionsByDate(String date) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        // Truy vấn tất cả giao dịch từ bảng Transactions
        Cursor cursor = db.query(
                CreateDatabase.TABLE_TRANSACTIONS,
                null, // Lấy tất cả các cột
                null, // Không có điều kiện WHERE ở đây
                null,
                null,
                null,
                null
        );

        // Định dạng ngày thành "dd/MM/yyyy"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_ID));
                int ingredientId = cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_INGREDIENT_ID));
                long transactionDateLong = cursor.getLong(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_DATE));
                String transactionType = cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_TYPE));
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_QUANTITY));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_UNIT));
                String note = cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.COLUMN_TRANSACTION_NOTE));

                // Chuyển long thành Date, rồi định dạng thành chuỗi ngày
                Date transactionDate = new Date(transactionDateLong);
                String formattedDate = dateFormat.format(transactionDate);

                // So sánh ngày đã định dạng với ngày nhập từ người dùng
                if (formattedDate.equals(date)) {
                    // Tạo đối tượng Transaction và thêm vào danh sách
                    Transaction transaction = new Transaction(id, ingredientId, transactionDateLong, transactionType, quantity, unit, note);
                    transactions.add(transaction);
                }
            }
            cursor.close(); // Đóng cursor sau khi dùng xong
        }

        return transactions; // Trả về danh sách giao dịch đã tìm thấy
    }
    //lay so tuong trong kho
    public int getCurrentIngredientQuantity(int ingredientId) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        int currentQuantity = 0;

        Cursor cursor = db.rawQuery(
                "SELECT (SUM(CASE WHEN " + CreateDatabase.COLUMN_TRANSACTION_TYPE + " = 'input' THEN " + CreateDatabase.COLUMN_TRANSACTION_QUANTITY + " ELSE 0 END) " +
                        "- SUM(CASE WHEN " + CreateDatabase.COLUMN_TRANSACTION_TYPE + " = 'output' THEN " + CreateDatabase.COLUMN_TRANSACTION_QUANTITY + " ELSE 0 END)) AS currentQuantity " +
                        "FROM " + CreateDatabase.TABLE_TRANSACTIONS +
                        " WHERE " + CreateDatabase.COLUMN_TRANSACTION_INGREDIENT_ID + " = ?",
                new String[]{String.valueOf(ingredientId)}
        );

        if (cursor != null && cursor.moveToFirst()) {
            currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow("currentQuantity"));
            cursor.close();
        }

        return currentQuantity;
    }
    public long addTransaction(Transaction transaction) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        // Thêm giao dịch vào bảng Transactions
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.COLUMN_TRANSACTION_INGREDIENT_ID, transaction.getIngredientId());
        values.put(CreateDatabase.COLUMN_TRANSACTION_DATE, transaction.getTransactionDate());
        values.put(CreateDatabase.COLUMN_TRANSACTION_TYPE, transaction.getTransactionType());
        values.put(CreateDatabase.COLUMN_TRANSACTION_QUANTITY, transaction.getQuantity());
        values.put(CreateDatabase.COLUMN_TRANSACTION_UNIT, transaction.getUnit());
        values.put(CreateDatabase.COLUMN_TRANSACTION_NOTE, transaction.getNote());

        return db.insert(CreateDatabase.TABLE_TRANSACTIONS, null, values); // Thêm giao dịch
    }
    //ham cap nhat so luong cua 1 nguyen lieu theo id
    public boolean updateIngredientQuantityById(int ingredientId, double newQuantity) {
        // Lấy SQLiteDatabase để thực hiện truy vấn
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();  // Dùng phương thức này để có quyền ghi vào DB

        // Tạo ContentValues để chứa các giá trị cần cập nhật
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.COLUMN_INGREDIENT_QUANTITY, newQuantity); // Cập nhật số lượng mới

        // Thực hiện cập nhật, truyền vào điều kiện WHERE để chỉ cập nhật bản ghi có id tương ứng
        int rowsAffected = db.update(CreateDatabase.TABLE_INGREDIENTS, contentValues,
                CreateDatabase.COLUMN_INGREDIENT_ID + " = ?", new String[]{String.valueOf(ingredientId)});

        // Đóng database sau khi xong
        db.close();

        // Kiểm tra kết quả cập nhật
        return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng bị ảnh hưởng
    }


}
