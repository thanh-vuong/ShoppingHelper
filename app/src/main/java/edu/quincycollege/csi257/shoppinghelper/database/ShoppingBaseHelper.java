package edu.quincycollege.csi257.shoppinghelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ShoppingBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "shoppingbase.db";

    public ShoppingBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + ShoppingDbSchema.ShoppingTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                        ShoppingDbSchema.ShoppingTable.Cols.UUID + ", " +
                        ShoppingDbSchema.ShoppingTable.Cols.UPC + ", " +
                        ShoppingDbSchema.ShoppingTable.Cols.ON_THE_LIST + ", " +
                        ShoppingDbSchema.ShoppingTable.Cols.BRAND + ", " +
                        ShoppingDbSchema.ShoppingTable.Cols.PRODUCT + ", " +
                        ShoppingDbSchema.ShoppingTable.Cols.UNIT + ", " +
                        ShoppingDbSchema.ShoppingTable.Cols.PRICE + ", " +
                        ShoppingDbSchema.ShoppingTable.Cols.PRICE_UNIT + ", " +
                        ShoppingDbSchema.ShoppingTable.Cols.PACKAGE_SIZE + ", " +
                        ShoppingDbSchema.ShoppingTable.Cols.PACKAGE_SIZE_UNIT + ", " +
                        ShoppingDbSchema.ShoppingTable.Cols.STORE +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
