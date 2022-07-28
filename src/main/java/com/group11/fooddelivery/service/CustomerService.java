package com.group11.fooddelivery.service;

import com.group11.fooddelivery.clients.AuthenticationClient;
import com.group11.fooddelivery.clients.CustomerClient;
import com.group11.fooddelivery.configure.Constants;
import com.group11.fooddelivery.model.Order;
import com.group11.fooddelivery.model.OrdersByUser;
import com.group11.fooddelivery.model.Restaurant;
import com.group11.fooddelivery.model.request.LatLongRequest;
import com.group11.fooddelivery.model.request.PlaceOrderRequest;
import com.group11.fooddelivery.model.response.LatLongResponse;
import com.group11.fooddelivery.model.response.PlaceOrderResponse;
import com.group11.fooddelivery.repository.OrdersByUsersRepository;
import com.group11.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }

    public PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest) {
        /*
        1. send an order Id
        2. store data in ordersByUsers and orderDetails
         */
        PlaceOrderResponse placeOrderResponse = new PlaceOrderResponse();

        //Verify session token.
        if (!authenticationClient.verifyToken(placeOrderRequest, placeOrderRequest.getEmail())) {
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

    public LatLongResponse getRestaurant(LatLongRequest latLongRequest) {
        LatLongResponse latLongResponse = new LatLongResponse();
        List<String> restaurantByName = new ArrayList<>();
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        double lat2 = latLongRequest.getLatitude();
        double lon2 = latLongRequest.getLongitude();

        for (int i = 0; i < restaurantList.size(); i++) {
            double lat1 = restaurantList.get(i).getLatitude();
            double lon1 = restaurantList.get(i).getLongitude();

            if (distance(lat1, lon1, lat2, lon2, "K") <= 5) {
                String res = restaurantList.get(i).getName();
                restaurantByName.add(res);

            }
        }
        latLongResponse.setListOfRestaurant(restaurantByName);
        return latLongResponse;

    }
}
