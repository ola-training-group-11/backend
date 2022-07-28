package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.Item;
import com.group11.fooddelivery.model.Restaurant;
import com.group11.fooddelivery.model.request.AddItemRequest;
import com.group11.fooddelivery.model.request.GetItemRequest;
import com.group11.fooddelivery.model.response.*;
import com.group11.fooddelivery.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @GetMapping("/updateStatus")
    @ResponseBody
    public ResponseEntity<UpdateStatusManagerResponse> updateStatus(@RequestParam(required = true) String orderId, @RequestParam(required = true) String status) {

        try {
            UpdateStatusManagerResponse updateStatusManagerResponse = managerService.updateStatus(orderId, status);
            if (updateStatusManagerResponse.isSuccess()) {
                return new ResponseEntity<UpdateStatusManagerResponse>(updateStatusManagerResponse, HttpStatus.OK);

            } else {
                return new ResponseEntity<UpdateStatusManagerResponse>(updateStatusManagerResponse, HttpStatus.BAD_REQUEST);

            }
        } catch (Exception e) {
            UpdateStatusManagerResponse updateStatusManagerResponse = null;
            return new ResponseEntity<UpdateStatusManagerResponse>(updateStatusManagerResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getActiveOrders")
    @ResponseBody
    public ResponseEntity<GetActiveOrdersResponse> getActiveOrders(@RequestParam(required = true) Long restaurantId) {

        try {
            GetActiveOrdersResponse getActiveOrdersResponse = managerService.getActiveOrders(restaurantId);
            if (getActiveOrdersResponse.isSuccess()) {
                return new ResponseEntity<GetActiveOrdersResponse>(getActiveOrdersResponse, HttpStatus.OK);

            } else {
                return new ResponseEntity<GetActiveOrdersResponse>(getActiveOrdersResponse, HttpStatus.BAD_REQUEST);

            }
        } catch (Exception e) {
            GetActiveOrdersResponse getActiveOrdersResponse = null;
            return new ResponseEntity<GetActiveOrdersResponse>(getActiveOrdersResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/updateRestaurantDetails", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UpdateRestaurantDetails> updateRestaurantDetails(@RequestBody Restaurant restaurant) {

        try {
            UpdateRestaurantDetails updateRestaurantDetails = managerService.updateRestaurantDetails(restaurant);
            if (updateRestaurantDetails.isSuccess()) {
                return new ResponseEntity<UpdateRestaurantDetails>(updateRestaurantDetails, HttpStatus.OK);

            } else {
                return new ResponseEntity<UpdateRestaurantDetails>(updateRestaurantDetails, HttpStatus.BAD_REQUEST);

            }
        } catch (Exception e) {
            UpdateRestaurantDetails updateRestaurantDetails = null;
            return new ResponseEntity<UpdateRestaurantDetails>(updateRestaurantDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/addItem", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AddItemResponse> AddItem(@RequestBody AddItemRequest addItemRequest) {
        AddItemResponse addItemResponse = managerService.addItem(addItemRequest);
        return new ResponseEntity<>(addItemResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/editItem", consumes = "application/json", produces = "application/json")
    public ResponseEntity<EditItemResponse> EditItem(@RequestBody Item item) {
        EditItemResponse editItemResponse = managerService.editItem(item);
        return new ResponseEntity<>(editItemResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/getItems", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GetItemResponse> getItems(@RequestBody GetItemRequest getItemRequest) {
        GetItemResponse getItemResponse = managerService.getAllItems(getItemRequest.getRestaurantId());
        return new ResponseEntity<>(getItemResponse, HttpStatus.OK);
    }
}
