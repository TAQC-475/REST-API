package com.softserve.edu.rest.data;

import java.util.ArrayList;
import java.util.List;

public class ItemRepository {

    //TODO add this repo to USER
    public static Item getDefaultItem() {
        return new Item("1", "Default Item With Index 1");
    }

    public static Item getCoreI5() {
        return new Item("1", "Intel Core i5");
    }

    public static Item getCoreI7() {
        return new Item("2", "Intel Core i7");
    }

    public static Item getCoreI9() {
        return new Item("3", "Intel Core i9");
    }

    public static List<Item> getTestItemsList() {
        List<Item> items = new ArrayList<>();
        items.add(getCoreI5());
        items.add(getCoreI7());
        return items;
    }

    public static List<String> getTestItemsIndexes(){
        List<String> itemsIndexes = new ArrayList<>();
        itemsIndexes.add(getCoreI5().getItemIndex());
        itemsIndexes.add(getCoreI7().getItemIndex());
        return itemsIndexes;
    }
}
