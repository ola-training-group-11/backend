package com.group11.fooddelivery.model.response;

import com.group11.fooddelivery.model.Item;

import java.util.List;

public class GetItemResponse extends Response{
    private List<Item> allItems;

    public List<Item> getAllItems() {
        return allItems;
    }

    public void setAllItems(List<Item> allItems) {
        this.allItems = allItems;
    }
}
