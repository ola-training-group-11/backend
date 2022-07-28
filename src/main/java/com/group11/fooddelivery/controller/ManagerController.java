package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.Restaurant;
import com.group11.fooddelivery.model.response.GetActiveOrdersManager;
import com.group11.fooddelivery.model.response.UpdateRestaurantDetails;
import com.group11.fooddelivery.model.response.UpdateStatusManagerResponse;
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
    public ResponseEntity<UpdateStatusManagerResponse> updateStatus(@RequestParam(required = true) String orderId, @RequestParam(required = true) String status){

        try {
            UpdateStatusManagerResponse updateStatusManagerResponse = managerService.updateStatus(orderId, status);
            if(updateStatusManagerResponse.isSuccess()){
                return  new ResponseEntity<UpdateStatusManagerResponse>(updateStatusManagerResponse, HttpStatus.OK);

            }else{
                return new ResponseEntity<UpdateStatusManagerResponse>(updateStatusManagerResponse, HttpStatus.BAD_REQUEST);

            }
        }catch (Exception e){
            UpdateStatusManagerResponse updateStatusManagerResponse = null;
            return new ResponseEntity<UpdateStatusManagerResponse>(updateStatusManagerResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getActiveOrdersManager")
    @ResponseBody
    public ResponseEntity<GetActiveOrdersManager> getActiveOrdersManager(@RequestParam(required = true) Long restaurantId){

        try {
            GetActiveOrdersManager getActiveOrdersManager = managerService.getActiveOrders(restaurantId);
            if(getActiveOrdersManager.isSuccess()){
                return  new ResponseEntity<GetActiveOrdersManager>(getActiveOrdersManager, HttpStatus.OK);

            }else{
                return new ResponseEntity<GetActiveOrdersManager>(getActiveOrdersManager, HttpStatus.BAD_REQUEST);

            }
        }catch (Exception e){
            GetActiveOrdersManager getActiveOrdersManager = null;
            return new ResponseEntity<GetActiveOrdersManager>(getActiveOrdersManager, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/updateRestaurantDetails", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UpdateRestaurantDetails> updateRestaurantDetails(@RequestBody Restaurant restaurant){

        try {
            UpdateRestaurantDetails updateRestaurantDetails = managerService.updateRestaurantDetails(restaurant);
            if(updateRestaurantDetails.isSuccess()){
                return  new ResponseEntity<UpdateRestaurantDetails>(updateRestaurantDetails, HttpStatus.OK);

            }else{
                return new ResponseEntity<UpdateRestaurantDetails>(updateRestaurantDetails, HttpStatus.BAD_REQUEST);

            }
        }catch (Exception e){
            UpdateRestaurantDetails updateRestaurantDetails = null;
            return new ResponseEntity<UpdateRestaurantDetails>(updateRestaurantDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
