package com.group11.fooddelivery.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrderRequestItems {
    private Long itemId;
    private int quantity;
}
