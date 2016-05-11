package edu.quincycollege.csi257.shoppinghelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ch on 5/8/2016.
 */
public class ShoppingList {
    private static ShoppingList sShoppingList;

    public static ShoppingList get() {
        if (sShoppingList == null)
            sShoppingList = new ShoppingList();
        return sShoppingList;
    }

    private List<Item> mShoppingList;

    private ShoppingList() {
        mShoppingList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Item item = new Item();
            item.setName("item #" + (i + 1));
            mShoppingList.add(item);
        }
    }

    private void addItem(Item newItem){
        mShoppingList.add(newItem);
    }

    private void removeItem(UUID id){
        for (int i = 0; i < mShoppingList.size(); i++)
            if (mShoppingList.get(i).getId().equals(id))
                mShoppingList.remove(i);
    }

    private void clearList(){
        mShoppingList.clear();
    }

    public List<Item> getShoppingList() {
        return mShoppingList;
    }

    public Item getClothingItem(UUID id) {
        for (Item item : mShoppingList)
            if (item.getId().equals(id))
                return item;
        return null;
    }
}
