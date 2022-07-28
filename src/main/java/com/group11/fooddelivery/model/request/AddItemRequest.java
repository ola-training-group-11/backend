package com.group11.fooddelivery.model.request;

import com.group11.fooddelivery.model.Item;

public class AddItemRequest {
    private Item item;
    private long restaurantId;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
