package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.response.AdminResponse;
import com.group11.fooddelivery.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
     @Autowired
    AdminService adminService;

    @GetMapping(value = "/getUser", consumes = "application/json" , produces = "application/json")
    public ResponseEntity<AdminResponse> getUsers(@PathVariable String field){
        AdminResponse adminResponse = adminService.getUsers(field);
       return new ResponseEntity<AdminResponse>(adminResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/getRestaurants", consumes = "application/json" , produces = "application/json")
    public ResponseEntity<AdminResponse> getRestaurants(@PathVariable String field){
      AdminResponse adminResponse =  adminService.getRestaurants(field);
        return new ResponseEntity<AdminResponse>(adminResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/getOrders", consumes = "application/json" , produces = "application/json")
    public ResponseEntity<AdminResponse> getOrders(@PathVariable String field){
      AdminResponse adminResponse = adminService.getOrders(field);
        return new ResponseEntity<AdminResponse>(adminResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/banRestaurant", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AdminResponse> banRestaurants(@RequestParam Long restaurantId){
      AdminResponse adminResponse = adminService.banRestaurant(restaurantId);
        return new ResponseEntity<AdminResponse>(adminResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/banRestaurant", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AdminResponse> banUser(@RequestParam Long userId){
    AdminResponse adminResponse =  adminService.banUser(userId);
     return new  ResponseEntity<AdminResponse>(adminResponse,HttpStatus.OK);
    }
}
