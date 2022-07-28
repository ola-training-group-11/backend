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
import com.group11.fooddelivery.model.response.TrackResponse;
import com.group11.fooddelivery.repository.OrdersByUsersRepository;
import com.group11.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.group11.fooddelivery.configure.Constants.*;

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
        if (!authenticationClient.verifyToken(placeOrderRequest)) {
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
        ordersByUser.setStatus(orderPlaced);
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

        //Verify session token.
        if (!authenticationClient.verifyToken(latLongRequest)) {
            latLongResponse.setSuccess(false);
            latLongResponse.setMessage("User session expired.");
            return latLongResponse;
        }

        List<Restaurant> restaurantByName = new ArrayList<>();
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        double lat2 = latLongRequest.getLatitude();
        double lon2 = latLongRequest.getLongitude();

        for (Restaurant restaurant : restaurantList) {
            double lat1 = restaurant.getLatitude();
            double lon1 = restaurant.getLongitude();

            if (customerClient.distance(lat1, lon1, lat2, lon2, "K") <= 5) {
                restaurantByName.add(restaurant);
            }
        }
        latLongResponse.setListOfRestaurant(restaurantByName);
        latLongResponse.setSuccess(true);
        latLongResponse.setMessage("Please find your nearest restaurants!");
        return latLongResponse;

    }
    public TrackResponse track(String OrderId) {
        OrdersByUser ordersByUser=ordersByUsersRepository.findByOrderId(OrderId);
        TrackResponse trackResponse=new TrackResponse();
        if(ordersByUser==null){
            trackResponse.setSuccess(false);
            trackResponse.setMessage("No order is placed !");
        }

        else if(ordersByUser.getStatus().equals(orderPlaced)){
            trackResponse.setSuccess(true);
            trackResponse.setMessage("Ordered is placed will be delivered soon");
        }
        else if(ordersByUser.getStatus().equals(orderInTransit)){
            trackResponse.setSuccess(true);
            trackResponse.setMessage("Ordered is on the way will be delivered soon");
        }
        else if(ordersByUser.getStatus().equals(orderDelivered)){
            trackResponse.setSuccess(true);
            trackResponse.setMessage("Ordered is Delivered");
        }
        else
        {
            trackResponse.setSuccess(false);
            trackResponse.setMessage("Something went wrong");
        }
        return trackResponse;
    }
}
