package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.request.LatLongRequest;
import com.group11.fooddelivery.model.request.PlaceOrderRequest;
import com.group11.fooddelivery.model.response.LatLongResponse;
import com.group11.fooddelivery.model.response.PlaceOrderResponse;
import com.group11.fooddelivery.model.response.TrackResponse;
import com.group11.fooddelivery.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/placeOrder", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PlaceOrderResponse> placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
        PlaceOrderResponse placeOrderResponse = customerService.placeOrder(placeOrderRequest);
        return new ResponseEntity<>(placeOrderResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/getRestaurants",consumes = "application/json",produces = "application/json")
    public ResponseEntity<LatLongResponse> getRestaurants(@RequestBody LatLongRequest latLongRequest){
        LatLongResponse latLongResponse=customerService.getRestaurant(latLongRequest);
        return new ResponseEntity<>(latLongResponse,HttpStatus.OK);

    }
    @GetMapping(value = "/trackOrder",produces = "application/json")
    public ResponseEntity<TrackResponse>trackOrder(@RequestParam String OrderId){
        TrackResponse trackResponse= customerService.track(OrderId);
        return new ResponseEntity<>(trackResponse,HttpStatus.OK);
    }
}
