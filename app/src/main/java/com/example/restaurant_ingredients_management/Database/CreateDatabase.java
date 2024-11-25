package com.example.restaurant_ingredients_management.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CreateDatabase extends SQLiteOpenHelper {

    // Tên cơ sở dữ liệu và phiên bản
    private static final String DATABASE_NAME = "icegozi.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và cột cho bảng Ingredient
    public static final String TABLE_INGREDIENTS = "ingredients";
    public static final String COLUMN_INGREDIENT_ID = "id";
    public static final String COLUMN_INGREDIENT_NAME = "name";
    public static final String COLUMN_INGREDIENT_IMAGE = "image_data";
    public static final String COLUMN_INGREDIENT_QUANTITY = "quantity";
    public static final String COLUMN_INGREDIENT_UNIT = "unit";
    public static final String COLUMN_INGREDIENT_EXPIRATION_DATE = "expirationDate";
    public static final String COLUMN_INGREDIENT_IS_LOW_STOCK = "isLowStock";
    public static final String COLUMN_INGREDIENT_LAST_UPDATED = "lastUpdated";

    // Tên bảng và cột cho bảng StockAlert
    public static final String TABLE_STOCK_ALERTS = "stock_alerts";
    public static final String COLUMN_STOCK_ALERT_ID = "id";
    public static final String COLUMN_STOCK_ALERT_INGREDIENT_ID = "ingredientId";
    public static final String COLUMN_STOCK_ALERT_TYPE = "alertType";
    public static final String COLUMN_STOCK_ALERT_DATE = "alertDate";
    public static final String COLUMN_STOCK_ALERT_IS_RESOLVED = "isResolved";

    // Tên bảng và cột cho bảng Transaction
    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String COLUMN_TRANSACTION_ID = "id";
    public static final String COLUMN_TRANSACTION_INGREDIENT_ID = "ingredientId";
    public static final String COLUMN_TRANSACTION_DATE = "transactionDate";
    public static final String COLUMN_TRANSACTION_TYPE = "transactionType";
    public static final String COLUMN_TRANSACTION_QUANTITY = "quantity";
    public static final String COLUMN_TRANSACTION_UNIT = "unit";
    public static final String COLUMN_TRANSACTION_NOTE = "note";

    // Tên bảng và cột cho bảng Supplier
    public static final String TABLE_SUPPLIERS = "suppliers";
    public static final String COLUMN_SUPPLIER_ID = "id";
    public static final String COLUMN_SUPPLIER_NAME = "name";
    public static final String COLUMN_SUPPLIER_CONTACT_INFO = "contactInfo";
    public static final String COLUMN_SUPPLIER_ADDRESS = "address";

    // Tên bảng và cột cho bảng IngredientSupplier
    public static final String TABLE_INGREDIENT_SUPPLIERS = "ingredient_suppliers";
    public static final String COLUMN_INGREDIENT_SUPPLIER_ID = "id";
    public static final String COLUMN_INGREDIENT_SUPPLIER_INGREDIENT_ID = "ingredientId";
    public static final String COLUMN_INGREDIENT_SUPPLIER_SUPPLIER_ID = "supplierId";
    public static final String COLUMN_INGREDIENT_SUPPLIER_PRICE_PER_UNIT = "pricePerUnit";
    public static final String COLUMN_INGREDIENT_SUPPLIER_SUPPLY_DATE = "supplyDate";



    public CreateDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Tạo bảng Ingredient
        String CREATE_TABLE_INGREDIENT = "CREATE TABLE " + TABLE_INGREDIENTS + " ("
                + COLUMN_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_INGREDIENT_NAME + " TEXT NOT NULL, "
                + COLUMN_INGREDIENT_IMAGE + " BLOB, "
                + COLUMN_INGREDIENT_QUANTITY + " REAL NOT NULL, "
                + COLUMN_INGREDIENT_UNIT + " TEXT, "
                + COLUMN_INGREDIENT_EXPIRATION_DATE + " INTEGER, "
                + COLUMN_INGREDIENT_IS_LOW_STOCK + " INTEGER, "
                + COLUMN_INGREDIENT_LAST_UPDATED + " INTEGER)";
        sqLiteDatabase.execSQL(CREATE_TABLE_INGREDIENT);

        // Tạo bảng StockAlert
        String CREATE_TABLE_STOCK_ALERT = "CREATE TABLE " + TABLE_STOCK_ALERTS + " ("
                + COLUMN_STOCK_ALERT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_STOCK_ALERT_INGREDIENT_ID + " INTEGER NOT NULL, "
                + COLUMN_STOCK_ALERT_TYPE + " TEXT NOT NULL, "
                + COLUMN_STOCK_ALERT_DATE + " INTEGER, "
                + COLUMN_STOCK_ALERT_IS_RESOLVED + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_STOCK_ALERT_INGREDIENT_ID + ") REFERENCES " + TABLE_INGREDIENTS + "(" + COLUMN_INGREDIENT_ID + "))";
        sqLiteDatabase.execSQL(CREATE_TABLE_STOCK_ALERT);

        // Tạo bảng Transaction
        String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TABLE_TRANSACTIONS + " ("
                + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TRANSACTION_INGREDIENT_ID + " INTEGER NOT NULL, "
                + COLUMN_TRANSACTION_DATE + " INTEGER, "
                + COLUMN_TRANSACTION_TYPE + " TEXT NOT NULL, "
                + COLUMN_TRANSACTION_QUANTITY + " REAL NOT NULL, "
                + COLUMN_TRANSACTION_UNIT + " TEXT, "
                + COLUMN_TRANSACTION_NOTE + " TEXT, "
                + "FOREIGN KEY(" + COLUMN_TRANSACTION_INGREDIENT_ID + ") REFERENCES " + TABLE_INGREDIENTS + "(" + COLUMN_INGREDIENT_ID + "))";
        sqLiteDatabase.execSQL(CREATE_TABLE_TRANSACTION);

        // Tạo bảng Supplier
        String CREATE_TABLE_SUPPLIER = "CREATE TABLE " + TABLE_SUPPLIERS + " ("
                + COLUMN_SUPPLIER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SUPPLIER_NAME + " TEXT NOT NULL, "
                + COLUMN_SUPPLIER_CONTACT_INFO + " TEXT, "
                + COLUMN_SUPPLIER_ADDRESS + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE_SUPPLIER);

        // Tạo bảng IngredientSupplier
        String CREATE_TABLE_INGREDIENT_SUPPLIER = "CREATE TABLE " + TABLE_INGREDIENT_SUPPLIERS + " ("
                + COLUMN_INGREDIENT_SUPPLIER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_INGREDIENT_SUPPLIER_INGREDIENT_ID + " INTEGER NOT NULL, "
                + COLUMN_INGREDIENT_SUPPLIER_SUPPLIER_ID + " INTEGER NOT NULL, "
                + COLUMN_INGREDIENT_SUPPLIER_PRICE_PER_UNIT + " REAL NOT NULL, "
                + COLUMN_INGREDIENT_SUPPLIER_SUPPLY_DATE + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_INGREDIENT_SUPPLIER_INGREDIENT_ID + ") REFERENCES " + TABLE_INGREDIENTS + "(" + COLUMN_INGREDIENT_ID + "), "
                + "FOREIGN KEY(" + COLUMN_INGREDIENT_SUPPLIER_SUPPLIER_ID + ") REFERENCES " + TABLE_SUPPLIERS + "(" + COLUMN_SUPPLIER_ID + "))";
        sqLiteDatabase.execSQL(CREATE_TABLE_INGREDIENT_SUPPLIER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT_SUPPLIERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPPLIERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCK_ALERTS);
        onCreate(sqLiteDatabase);
    }

    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}
