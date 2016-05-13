package edu.quincycollege.csi257.shoppinghelper;

import java.io.File;
import java.util.UUID;

/**
 * Created by Chico on 5/6/2016.
 */
public class Item {

    private UUID id;
    private long upc;
    private boolean onTheList;
    private String brand;
    private String product;
    private int unit;
    private double price;
    private int priceUnit;
    private double packageSize;
    private String packageSizeUnit;
    private String store;


    public Item() {
        this(UUID.randomUUID());
    }

    public Item(UUID id) {

        this.id = id;
        upc = 000000000000;
        onTheList = true;
        brand = "";
        product = "";
        unit = 0;
        price = 0;
        priceUnit = 0;
        packageSize = 0;
        packageSizeUnit = "";
        store = "";

    }

    public void setUpc(long upc) {
        this.upc = upc;
    }

    public void setOnTheList(boolean shoppingList) {
        this.onTheList = shoppingList;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setProduct(String name) {
        this.product = name;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPriceUnit(int priceUnit) {
        this.priceUnit = priceUnit;
    }

    public void setPackageSize(double packageSize) {
        this.packageSize = packageSize;
    }

    public void setPackageSizeUnit(String packageSizeUnit) {
        this.packageSizeUnit = packageSizeUnit;
    }

    public void setStore(String store) {
        this.store = store;
    }


    public UUID getId() {
        return id;
    }

    public long getUpc() {
        return upc;
    }

    public boolean isOnTheList() {
        return onTheList;
    }

    public String getBrand() {
        return brand;
    }

    public String getProduct() {
        return product;
    }

    public int getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }

    public int getPriceUnit() {
        return priceUnit;
    }

    public double getPackageSize() {
        return packageSize;
    }

    public String getPackageSizeUnit() {
        return packageSizeUnit;
    }

    public String getStore() {
        return store;
    }



}
