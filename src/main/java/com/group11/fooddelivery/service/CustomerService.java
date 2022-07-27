package com.group11.fooddelivery.service;

import com.group11.fooddelivery.model.request.PlaceOrderRequest;
import com.group11.fooddelivery.model.response.PlaceOrderResponse;
import com.group11.fooddelivery.repository.OrderRepository;
import com.group11.fooddelivery.repository.OrdersByUsersRepository;
import com.group11.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrdersByUsersRepository ordersByUsersRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    public PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest)   {
        /*
        1. send an order Id
        2. store data in ordersByUsers and orderDetails
         */
        PlaceOrderResponse placeOrderResponse = new PlaceOrderResponse();
        return placeOrderResponse;
    }
}
