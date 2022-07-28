package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.request.PlaceOrderRequest;
import com.group11.fooddelivery.model.response.PlaceOrderResponse;
import com.group11.fooddelivery.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/placeOrder", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PlaceOrderResponse> placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
        PlaceOrderResponse placeOrderResponse = customerService.placeOrder(placeOrderRequest);
        return new ResponseEntity<>(placeOrderResponse, HttpStatus.OK);
    }
}
