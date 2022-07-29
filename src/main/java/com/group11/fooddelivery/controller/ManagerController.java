package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.Item;
import com.group11.fooddelivery.model.Restaurant;
import com.group11.fooddelivery.model.request.AddItemRequest;
import com.group11.fooddelivery.model.request.GetActiveOrdersManagerRequest;
import com.group11.fooddelivery.model.request.UpdateStatusRequest;
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
    public ResponseEntity<UpdateStatusManagerResponse> updateStatus(@RequestBody UpdateStatusRequest updateStatusRequest){

        try {
            UpdateStatusManagerResponse updateStatusManagerResponse = managerService.updateStatus(updateStatusRequest.getOrderId(), updateStatusRequest.getStatus());
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

    @GetMapping("/getActiveOrdersManager")
    @ResponseBody
    public ResponseEntity<GetActiveOrdersManager> getActiveOrdersManager(@RequestBody GetActiveOrdersManagerRequest getActiveOrdersManagerRequest) {

        try {
            GetActiveOrdersManager getActiveOrdersManager = managerService.getActiveOrders(getActiveOrdersManagerRequest.getRestaurantId());
            if (getActiveOrdersManager.isSuccess()) {
                return new ResponseEntity<GetActiveOrdersManager>(getActiveOrdersManager, HttpStatus.OK);

            } else {
                return new ResponseEntity<GetActiveOrdersManager>(getActiveOrdersManager, HttpStatus.BAD_REQUEST);

            }
        } catch (Exception e) {
            GetActiveOrdersManager getActiveOrdersManager = null;
            return new ResponseEntity<GetActiveOrdersManager>(getActiveOrdersManager, HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping(value = "/getItems/{id}", produces = "application/json")
    public ResponseEntity<GetItemResponse> get_items(@PathVariable long id) {
        GetItemResponse getItemResponse = managerService.getAllItems(id);
        return new ResponseEntity<>(getItemResponse, HttpStatus.OK);
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
}
