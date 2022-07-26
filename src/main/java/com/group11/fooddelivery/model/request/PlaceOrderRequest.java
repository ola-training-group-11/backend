package com.group11.fooddelivery.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrderRequest extends Request{
    private Long restaurantId;
    private String selectedItems; //@todo discuss with Kunal if this will be passed in request as string or list.
}
