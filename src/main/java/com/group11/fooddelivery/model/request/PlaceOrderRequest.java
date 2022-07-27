package com.group11.fooddelivery.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceOrderRequest extends Request {
    private Long restaurantId;
    private List<itemDetail> selectedItems;

    static class itemDetail {
        private Long itemId;
        private int quantity;
    }
}