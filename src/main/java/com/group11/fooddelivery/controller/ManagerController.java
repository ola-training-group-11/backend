package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.Item;
import com.group11.fooddelivery.model.ItemsByRestaurant;
import com.group11.fooddelivery.model.response.ItemByRestaurantResponse;
import com.group11.fooddelivery.model.request.ManagerGetItemsRequest;
import com.group11.fooddelivery.model.response.Response;
import com.group11.fooddelivery.repository.ItemRepository;
import com.group11.fooddelivery.repository.ItemsByRestaurantRepository;
import com.group11.fooddelivery.service.ItemByIdService;
import com.group11.fooddelivery.service.ItemByRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ManagerController {

    Response response = new Response();
    @Autowired
    ItemByRestaurantService service;
    @Autowired
    ItemByIdService service2;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemsByRestaurantRepository itemsByRestaurantRepository;

    @GetMapping(value = "/getitems/{manager_id}")
    public List<Item> placeOrder(@PathVariable long manager_id) {
        List<ItemsByRestaurant> itemsByRestaurantList = new ArrayList<>();
        itemsByRestaurantList = (ArrayList) itemsByRestaurantRepository.findAllByRestaurantId(manager_id);
        ArrayList<Item> full_item = new ArrayList<>();

        for (ItemsByRestaurant i : itemsByRestaurantList) {

            System.out.println("item is " + i.getItemId());
            full_item.add(itemRepository.getByItemId(i.getItemId()));
        }
        return full_item;
    }

    @PostMapping(value = "/additem/{restraunt_id}", consumes = "application/json", produces = "application/json")
    public Response addItem(@RequestBody Item item, @PathVariable long restraunt_id) {

        itemRepository.save(item);
        response.setMessage("Good to go");

        ItemsByRestaurant itemsByRestaurant = new ItemsByRestaurant();
        itemsByRestaurant.setItemId(item.getItemId());
        itemsByRestaurant.setRestaurantId(restraunt_id);
        itemsByRestaurantRepository.save(itemsByRestaurant);
        return response;
    }
}


