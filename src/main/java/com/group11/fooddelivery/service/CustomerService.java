package com.group11.fooddelivery.service;

import com.group11.fooddelivery.clients.AuthenticationClient;
import com.group11.fooddelivery.clients.CustomerClient;
import com.group11.fooddelivery.configure.Constants;
import com.group11.fooddelivery.model.Order;
import com.group11.fooddelivery.model.OrdersByUser;
import com.group11.fooddelivery.model.Restaurant;
import com.group11.fooddelivery.model.request.PlaceOrderRequest;
import com.group11.fooddelivery.model.response.PlaceOrderResponse;
import com.group11.fooddelivery.repository.OrdersByUsersRepository;
import com.group11.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    OrdersByUsersRepository ordersByUsersRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    CustomerClient customerClient;
    @Autowired
    AuthenticationClient authenticationClient;

    public PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest) {
        /*
        1. send an order Id
        2. store data in ordersByUsers and orderDetails
         */
        PlaceOrderResponse placeOrderResponse = new PlaceOrderResponse();

        //Verify session token.
        if(!authenticationClient.verifyToken(placeOrderRequest, placeOrderRequest.getEmail()))  {
            placeOrderResponse.setSuccess(false);
            placeOrderResponse.setMessage("User session expired.");
            return placeOrderResponse;
        }

        Restaurant restaurant = restaurantRepository.findById(placeOrderRequest.getRestaurantId()).orElse(null);
        assert restaurant != null;

        //Generate random orderId
        String orderId = UUID.randomUUID().toString();

        //Populate ordersByUser table
        OrdersByUser ordersByUser = new OrdersByUser();
        ordersByUser.setOrderId(orderId);
        ordersByUser.setEmail(placeOrderRequest.getEmail());
        ordersByUser.setStatus(Constants.orderPlaced);
        ordersByUsersRepository.save(ordersByUser);

        //Populate orders_info table
        Order order = new Order();
        order.setOrderId(orderId);
        order.setRestaurantId(placeOrderRequest.getRestaurantId());
        customerClient.populateOrderInfo(order, placeOrderRequest.getSelectedItems());

        placeOrderResponse.setRestaurantName(restaurant.getName());
        placeOrderResponse.setOrderId(orderId);
        placeOrderResponse.setSelectedItems(customerClient.buildSelectedItems(placeOrderRequest.getSelectedItems()));
        int totalPrice = customerClient.calculateTotalPrice(placeOrderRequest.getSelectedItems());

        placeOrderResponse.setTotalPrice(totalPrice);
        placeOrderResponse.setSuccess(true);
        placeOrderResponse.setMessage("Order placed. Please find the summary!");
        return placeOrderResponse;
    }
}
