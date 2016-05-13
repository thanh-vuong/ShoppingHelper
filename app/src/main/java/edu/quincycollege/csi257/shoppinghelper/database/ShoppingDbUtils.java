package edu.quincycollege.csi257.shoppinghelper.database;

import android.content.ContentValues;


import edu.quincycollege.csi257.shoppinghelper.Item;

/**
 * Created by Instructor on 4/26/2016.
 */
public class ShoppingDbUtils {
    public static ContentValues getContentValues(Item item) {
        ContentValues values = new ContentValues();

        values.put(ShoppingDbSchema.ShoppingTable.Cols.UUID, item.getId().toString());
        values.put(ShoppingDbSchema.ShoppingTable.Cols.UPC, Long.toString(item.getUpc()));
        values.put(ShoppingDbSchema.ShoppingTable.Cols.ON_THE_LIST, item.isOnTheList() ? 1 : 0);
        values.put(ShoppingDbSchema.ShoppingTable.Cols.BRAND, item.getBrand());
        values.put(ShoppingDbSchema.ShoppingTable.Cols.PRODUCT, item.getProduct());
        values.put(ShoppingDbSchema.ShoppingTable.Cols.UNIT, Integer.toString(item.getUnit()));
        values.put(ShoppingDbSchema.ShoppingTable.Cols.PRICE, Double.toString(item.getPrice()));
        values.put(ShoppingDbSchema.ShoppingTable.Cols.PRICE_UNIT, item.getPriceUnit());
        values.put(ShoppingDbSchema.ShoppingTable.Cols.PACKAGE_SIZE, Double.toString(item.getPackageSize()));
        values.put(ShoppingDbSchema.ShoppingTable.Cols.PACKAGE_SIZE_UNIT, item.getPackageSizeUnit());
        values.put(ShoppingDbSchema.ShoppingTable.Cols.STORE, item.getStore());

        return values;
    }
}
