package edu.quincycollege.csi257.shoppinghelper.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import edu.quincycollege.csi257.shoppinghelper.Item;


public class ShoppingCursorWrapper extends CursorWrapper {
    public ShoppingCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Item getShoppingItem() {
        String uuidString = getString(getColumnIndex(ShoppingDbSchema.ShoppingTable.Cols.UUID));
        String upc = getString(getColumnIndex(ShoppingDbSchema.ShoppingTable.Cols.UPC));
        int onTheList = getInt(getColumnIndex(ShoppingDbSchema.ShoppingTable.Cols.ON_THE_LIST));
        String brand = getString(getColumnIndex(ShoppingDbSchema.ShoppingTable.Cols.BRAND));
        String product = getString(getColumnIndex(ShoppingDbSchema.ShoppingTable.Cols.PRODUCT));
        int unit = getInt(getColumnIndex(ShoppingDbSchema.ShoppingTable.Cols.UNIT));
        double price = getDouble(getColumnIndex(ShoppingDbSchema.ShoppingTable.Cols.PRICE));
        String priceUnit = getString(getColumnIndex(ShoppingDbSchema.ShoppingTable.Cols.PRICE_UNIT));
        double packageSize = getDouble(getColumnIndex(ShoppingDbSchema.ShoppingTable.Cols.PACKAGE_SIZE));
        String packageSizeUnit = getString(getColumnIndex(ShoppingDbSchema.ShoppingTable.Cols.PACKAGE_SIZE_UNIT));
        String store = getString(getColumnIndex(ShoppingDbSchema.ShoppingTable.Cols.STORE));

        Item shoppingItem = new Item(UUID.fromString(uuidString));
        shoppingItem.setUpc(upc);
        shoppingItem.setOnTheList(onTheList != 0);
        shoppingItem.setBrand(brand);
        shoppingItem.setName(product);
        shoppingItem.setUnit(unit);
        shoppingItem.setPrice(price);
        shoppingItem.setPriceUnit(priceUnit);
        shoppingItem.setPackageSize(packageSize);
        shoppingItem.setPackageSizeUnit(packageSizeUnit);
        shoppingItem.setStore(store);

        return shoppingItem;
    }
}
