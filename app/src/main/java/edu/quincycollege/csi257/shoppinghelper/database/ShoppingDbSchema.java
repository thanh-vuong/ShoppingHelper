package edu.quincycollege.csi257.shoppinghelper.database;

public class ShoppingDbSchema {
    public static final class ShoppingTable {
        public static final String NAME = "clothing";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String UPC = "upc";
            public static final String ON_THE_LIST = "shopping_list";
            public static final String BRAND = "brand";
            public static final String PRODUCT = "product";
            public static final String UNIT = "unit";
            public static final String PRICE = "price";
            public static final String PRICE_UNIT = "price_unit";
            public static final String PACKAGE_SIZE = "package_size";
            public static final String PACKAGE_SIZE_UNIT = "price_size_unit";
            public static final String STORE = "store";
        }
    }
}
