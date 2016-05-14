package edu.quincycollege.csi257.shoppinghelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.UUID;

import edu.quincycollege.csi257.shoppinghelper.database.ShoppingBaseHelper;
import edu.quincycollege.csi257.shoppinghelper.database.ShoppingCursorWrapper;
import edu.quincycollege.csi257.shoppinghelper.database.ShoppingDbSchema;
import edu.quincycollege.csi257.shoppinghelper.database.ShoppingDbUtils;

/**
 * Created by Ch on 5/8/2016.
 */
public class ShoppingList {
    private static ShoppingList sShoppingList;

    public static ShoppingList get(Context context) {
        if (sShoppingList == null)
            sShoppingList = new ShoppingList(context);
        return sShoppingList;
    }


    private SQLiteDatabase mShoppingDb;
    private Random random = new Random();

    private ShoppingList(Context context) {
        mShoppingDb = new ShoppingBaseHelper(context.getApplicationContext()).getWritableDatabase();

        for (int i = 0; i < 20; i++) {
            Item item = new Item();
            item.setUpc((i + 100000000000L));
            item.setOnTheList(random.nextBoolean());
            item.setBrand("band#" + (i + 1));
            item.setProduct("item#" + (i + 1));
            item.setUnit(1);
            item.setPrice(5.29);
            item.setPriceUnit(1);
            item.setPackageSize(128);
            item.setPackageSizeUnit("fl oz");
            item.setStore("Star Market");

            addShoppingItem(item);
        }
    }

    private void addItem(Item newItem){

        ContentValues values = ShoppingDbUtils.getContentValues(newItem);
        mShoppingDb.insert(ShoppingDbSchema.ShoppingTable.NAME, null, values);
    }

    private void removeItem(UUID id){

        mShoppingDb.delete(ShoppingDbSchema.ShoppingTable.NAME,
                ShoppingDbSchema.ShoppingTable.Cols.UUID + "=?",
                new String[]{id.toString()});
    }

    private void clearList(){
        ShoppingCursorWrapper cursor = queryShopping(null, null);
        Item updateItem;
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                updateItem = cursor.getShoppingItem();
                updateItem.setOnTheList(false);
                updateClothingItem(updateItem);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
    }

    public Item getClothingItem(UUID id) {

        ShoppingCursorWrapper cursor = queryShopping(
                ShoppingDbSchema.ShoppingTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getShoppingItem();
        } finally {
            cursor.close();
        }

    }

    public List<Item> getItemList() {

        ArrayList<Item> mClothingList = new ArrayList<Item>();
        ShoppingCursorWrapper cursor = queryShopping(
                ShoppingDbSchema.ShoppingTable.Cols.ON_THE_LIST + " = ?",
                new String[]{"1"});

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mClothingList.add(cursor.getShoppingItem());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return mClothingList;

    }

    public List<Item> getShoppingList() {

        ArrayList<Item> mClothingList = new ArrayList<Item>();
        ShoppingCursorWrapper cursor = queryShopping(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mClothingList.add(cursor.getShoppingItem());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return mClothingList;

    }

    public void addShoppingItem(Item item) {
        ContentValues values = ShoppingDbUtils.getContentValues(item);
        mShoppingDb.insert(ShoppingDbSchema.ShoppingTable.NAME, null, values);
    }

    public void updateClothingItem(Item item) {
        String uuidString = item.getId().toString();
        ContentValues values = ShoppingDbUtils.getContentValues(item);
        mShoppingDb.update(ShoppingDbSchema.ShoppingTable.NAME, values,
                ShoppingDbSchema.ShoppingTable.Cols.UUID + " = ? ",
                new String[]{uuidString});
    }

    private ShoppingCursorWrapper queryShopping(String whereClause, String[] whereArgs) {
        Cursor cursor = mShoppingDb.query(
                ShoppingDbSchema.ShoppingTable.NAME,
                null, // all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new ShoppingCursorWrapper(cursor);
    }

}
