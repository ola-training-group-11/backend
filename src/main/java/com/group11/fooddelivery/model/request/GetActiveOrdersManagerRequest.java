package com.group11.fooddelivery.model.request;

public class GetActiveOrdersManagerRequest {
    Long restaurantId;

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
