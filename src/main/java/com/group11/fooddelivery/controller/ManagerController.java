package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.Item;
import com.group11.fooddelivery.model.ItemsByRestaurant;
import com.group11.fooddelivery.model.response.ItemByRestaurantResponse;
import com.group11.fooddelivery.model.request.ManagerGetItemsRequest;
import com.group11.fooddelivery.model.response.Response;
import com.group11.fooddelivery.service.ItemByIdService;
import com.group11.fooddelivery.service.ItemByRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
@RestController
public class ManagerController
{

    @Autowired
    ItemByRestaurantService service ;
    @Autowired
    ItemByIdService   service2;
    @PostMapping(value = "/getitems", consumes = "application/json", produces = "application/json")
    public ArrayList<Item> placeOrder(@RequestBody ManagerGetItemsRequest request)
    {
        ItemByRestaurantResponse res  = service.getAllItemIds(request.getRestaurantId());
        ArrayList<Item> full_item = new ArrayList<>();

        for(ItemsByRestaurant i:res.getL())
        {

            System.out.print(i.getItemId());
            full_item.add(service2.getItemById(i.getItemId()));
        }
        return full_item;
    }
}
