package com.group11.fooddelivery.service;

import com.group11.fooddelivery.model.response.ItemByIdResponse;
import com.group11.fooddelivery.model.response.ItemByRestaurantResponse;
import com.group11.fooddelivery.model.ItemsByRestaurant;
import com.group11.fooddelivery.repository.ItemsByRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemByRestaurantService
{
    @Autowired
    ItemsByRestaurantRepository itemsByRestaurantRepository;
    ItemByRestaurantResponse  r = new ItemByRestaurantResponse();
    ItemByIdResponse res = new ItemByIdResponse();
    public ItemByRestaurantResponse getAllItemIds(long restaurant_id)
    {

        List<ItemsByRestaurant> itemsByRestaurantList = itemsByRestaurantRepository.findAllByItemId(restaurant_id);
        r.setL(itemsByRestaurantList);
        return r;

    }

}
