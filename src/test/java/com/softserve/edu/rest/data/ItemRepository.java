package com.softserve.edu.rest.data;

public class ItemRepository {

    //TODO add this repo to USER
    public static Item getDefaultItem() {
        return new Item("1", "Default Item With Index 1");
    }

    public static Item getCoreI5(){
        return new Item("1", "Intel Core i5");
    }

    public static Item getCoreI7(){
        return new Item("1", "Intel Core i7");
    }

    public static Item getCoreI9(){
        return new Item("1", "Intel Core i9");
    }

}
