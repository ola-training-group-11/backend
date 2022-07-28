package com.group11.fooddelivery.model.request;


public class ManagerGetItemsRequest extends Request{
    long restaurantId;

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
