package com.group11.fooddelivery.model.response;

import java.util.List;

public class PlaceOrderResponse extends Response{
    private String restaurantName;
    private List<itemDetail> selectedItems;
    private int totalPrice;

    static class itemDetail {
        private Long itemId;
        private int quantity;
    }
}
