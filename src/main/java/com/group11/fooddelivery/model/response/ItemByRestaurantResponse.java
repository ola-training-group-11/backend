package com.group11.fooddelivery.model.response;

import com.group11.fooddelivery.model.ItemsByRestaurant;
import org.apache.catalina.connector.Response;

import java.util.ArrayList;
import java.util.List;

public class ItemByRestaurantResponse extends Response
{
    List<ItemsByRestaurant> l = new ArrayList<>();

    public List<ItemsByRestaurant> getL() {
        return l;
    }

    public void setL(List<ItemsByRestaurant> l) {
        this.l = l;
    }
}
