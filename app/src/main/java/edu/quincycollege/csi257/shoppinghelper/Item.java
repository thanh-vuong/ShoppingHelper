package edu.quincycollege.csi257.shoppinghelper;

import java.io.File;
import java.util.UUID;

/**
 * Created by student on 5/6/2016.
 */
public class Item {

    private String brand;
    private String name;
    private double price;
    private int priceUnit;
    private int packageSize;
    private String packageSizeUnit;
    private String store;
    private UUID id;
    private File image;

    public Item() {
        brand = "";
        id = UUID.randomUUID();
        name = "";
        packageSize = 0;
        packageSizeUnit = "";
        price = 0;
        priceUnit = 0;
        store = "";
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public UUID getId() {
        return id;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(int packageSize) {
        this.packageSize = packageSize;
    }

    public String getPackageSizeUnit() {
        return packageSizeUnit;
    }

    public void setPackageSizeUnit(String packageSizeUnit) {
        this.packageSizeUnit = packageSizeUnit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(int priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }
}
