package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.request.PlaceOrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @PostMapping(value = "/placeOrder", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SignupResponse> signUp(@RequestBody PlaceOrderRequest placeOrderRequest) {
        SignupResponse signupResponse = userService.register(signupRequest);
        return signupResponse.isSuccess()
                ? new ResponseEntity<>(signupResponse, HttpStatus.OK)
                : new ResponseEntity<>(signupResponse, HttpStatus.BAD_REQUEST);
    }
}
