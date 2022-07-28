package com.group11.fooddelivery.service;

import com.group11.fooddelivery.model.Restaurant;
import com.group11.fooddelivery.model.request.LatLongRequest;
import com.group11.fooddelivery.model.request.PlaceOrderRequest;
import com.group11.fooddelivery.model.response.LatLongResponse;
import com.group11.fooddelivery.model.response.PlaceOrderResponse;
import com.group11.fooddelivery.repository.OrderRepository;
import com.group11.fooddelivery.repository.OrdersByUsersRepository;
import com.group11.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrdersByUsersRepository ordersByUsersRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    public PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest) {
        /*
        1. send an order Id
        2. store data in ordersByUsers and orderDetails
         */
        PlaceOrderResponse placeOrderResponse = new PlaceOrderResponse();
        return placeOrderResponse;
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
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

    public LatLongResponse getRestaurant(LatLongRequest latLongRequest) {
       LatLongResponse latLongResponse=new LatLongResponse();
       List<String> restaurantByName=new ArrayList<>();
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        double lat2=latLongRequest.getLatitude();
        double lon2=latLongRequest.getLongitude();

        for (int i = 0; i < restaurantList.size(); i++) {
            double lat1 = restaurantList.get(i).getLatitude();
            double lon1 = restaurantList.get(i).getLongitude();

            if(distance(lat1,lon1,lat2,lon2,"K")<=5){
              String res=restaurantList.get(i).getName();
              restaurantByName.add(res);

            }
        }
        latLongResponse.setListOfRestaurant(restaurantByName);
       return latLongResponse;

    }
}
