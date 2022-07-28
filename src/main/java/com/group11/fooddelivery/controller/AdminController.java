package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.response.AdminResponse;
import com.group11.fooddelivery.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
     @Autowired
    AdminService adminService;

    @GetMapping(value = "/getUser", produces = "application/json")
    public ResponseEntity<AdminResponse> getUsers(){
        AdminResponse adminResponse = adminService.getUsers();
       return new ResponseEntity<AdminResponse>(adminResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllRestaurants", produces = "application/json")
    public ResponseEntity<AdminResponse> getRestaurants(){
      AdminResponse adminResponse =  adminService.getAllRestaurants();
        return new ResponseEntity<AdminResponse>(adminResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/getOrders", produces = "application/json")
    public ResponseEntity<AdminResponse> getOrders(){
      AdminResponse adminResponse = adminService.getOrders();
        return new ResponseEntity<AdminResponse>(adminResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/banRestaurant", produces = "application/json")
    public ResponseEntity<AdminResponse> banRestaurants(@RequestParam long restaurantId){
      AdminResponse adminResponse = adminService.banRestaurant(restaurantId);
        return new ResponseEntity<AdminResponse>(adminResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/banUser", produces = "application/json")
    public ResponseEntity<AdminResponse> banUser(@RequestParam String email){
    AdminResponse adminResponse =  adminService.banUser(email);
     return new  ResponseEntity<AdminResponse>(adminResponse,HttpStatus.OK);
    }
}
