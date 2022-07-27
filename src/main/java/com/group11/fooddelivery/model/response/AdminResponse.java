package com.group11.fooddelivery.model.response;

import com.group11.fooddelivery.model.Order;
import com.group11.fooddelivery.model.Restaurant;
import com.group11.fooddelivery.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminResponse extends Response{
    List<User> users;
    List<Restaurant> restaurants;
    List<Order> orders;
}
