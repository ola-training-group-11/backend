package com.group11.fooddelivery.model.response;

public class PlaceOrderResponse extends Response{
    private String restaurantName;
    private String selectedItems; //@todo Discuss with Kunal if front end need selected items as list or string in response?
    private int totalPrice;
}
