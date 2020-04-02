package com.softserve.edu.rest.data;

public class Item {
    private String itemIndex;
    private String itemText;

    public Item(String itemIndex, String itemText) {
        this.itemIndex = itemIndex;
        this.itemText = itemText;
    }

    public String getItemIndex() {
        return itemIndex;
    }

    public Item setItemIndex(String itemIndex) {
        this.itemIndex = itemIndex;
        return this;
    }

    public String getItemText() {
        return itemText;
    }

    public Item setItemText(String itemText) {
        this.itemText = itemText;
        return this;

    }

    @Override
    public String toString() {
        return "Item{" +
                "itemIndex='" + itemIndex + '\'' +
                ", itemText='" + itemText + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (itemIndex != null ? !itemIndex.equals(item.itemIndex) : item.itemIndex != null) return false;
        return itemText != null ? itemText.equals(item.itemText) : item.itemText == null;
    }

    @Override
    public int hashCode() {
        int result = itemIndex != null ? itemIndex.hashCode() : 0;
        result = 31 * result + (itemText != null ? itemText.hashCode() : 0);
        return result;
    }
}
