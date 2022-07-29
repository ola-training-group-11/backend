package com.group11.fooddelivery.model.request;

import com.group11.fooddelivery.model.PlaceOrderRequestItems;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MakePaymentRequest extends Request{
    private String paymentMethod;
    private Long restaurantId;
    private List<PlaceOrderRequestItems> selectedItems;
}
