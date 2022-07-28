package com.group11.fooddelivery.model.response;

import com.group11.fooddelivery.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemByIdResponse
{
    List<Item> all_items = new ArrayList<>();

    public List<Item> getAll_items() {
        return all_items;
    }

    public void setAll_items(List<Item> all_items) {
        this.all_items = all_items;
    }
}
