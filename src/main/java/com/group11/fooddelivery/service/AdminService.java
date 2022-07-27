package com.group11.fooddelivery.service;

import com.group11.fooddelivery.model.Order;
import com.group11.fooddelivery.model.Restaurant;
import com.group11.fooddelivery.model.User;
import com.group11.fooddelivery.model.response.AdminResponse;
import com.group11.fooddelivery.repository.OrderRepository;
import com.group11.fooddelivery.repository.RestaurantRepository;
import com.group11.fooddelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    OrderRepository orderRepository;

    public AdminResponse getUsers(String field){
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.ASC,field));
        AdminResponse adminResponse = new AdminResponse();
        if(users == null){
            adminResponse.setUsers(null);
            adminResponse.setSuccess(false);
            adminResponse.setMessage("No User Exist in DataBase!!");
        }else {
            adminResponse.setUsers(users);
            adminResponse.setSuccess(true);
            adminResponse.setMessage("Users Fetched Successfully!!");
        }
        return adminResponse;
    }

    public AdminResponse getRestaurants(String field){
        List<Restaurant> restaurants = restaurantRepository.findAll(Sort.by(Sort.Direction.ASC,field));
        AdminResponse adminResponse =  new AdminResponse();
        if(restaurants == null){
            adminResponse.setRestaurants(null);
            adminResponse.setSuccess(false);
            adminResponse.setMessage("No Restaurants Exist in DataBase!!");
        }else{
            adminResponse.setRestaurants(restaurants);
            adminResponse.setSuccess(true);
            adminResponse.setMessage("Restaurants Fetched Successfully!!");
        }
        return adminResponse;
    }

    public AdminResponse getOrders(String field){
        List<Order> orders = orderRepository.findAll(Sort.by(Sort.Direction.ASC,field));
        AdminResponse adminResponse =  new AdminResponse();
        if(orders == null){
            adminResponse.setOrders(null);
            adminResponse.setSuccess(false);
            adminResponse.setMessage("No Orders Exist in DataBase!!");
        }else {
            adminResponse.setOrders(orders);
            adminResponse.setSuccess(true);
            adminResponse.setMessage("Orders Fetched Successfully!!");
        }
     return adminResponse;
    }

    public AdminResponse banRestaurant(Long restaurantId){
        Restaurant currentRestaurant = restaurantRepository.getById(restaurantId);
        AdminResponse adminResponse =  new AdminResponse();
        if (currentRestaurant == null){
            adminResponse.setSuccess(false);
            adminResponse.setMessage("Restaurant Doesn't exist!!!");
        }else if(!currentRestaurant.isActive()){
            adminResponse.setSuccess(false);
            adminResponse.setMessage("Restaurant is already banned!!");
        }else {
            restaurantRepository.delete(currentRestaurant);
            currentRestaurant.setActive(false);
            restaurantRepository.save(currentRestaurant);

            adminResponse.setSuccess(true);
            adminResponse.setMessage("Restaurant banned Successfully!!");
        }
        return adminResponse;
    }

    public AdminResponse banUser(Long userId){
        User currentUser = userRepository.getById(userId);
        AdminResponse adminResponse =  new AdminResponse();
        if (currentUser == null){
            adminResponse.setSuccess(false);
            adminResponse.setMessage("User Doesn't exist!!!");
        }else if(!currentUser.isActive()){
            adminResponse.setSuccess(false);
            adminResponse.setMessage("User is already banned!!");
        }else {
            userRepository.delete(currentUser);
            currentUser.setActive(false);
            userRepository.save(currentUser);

            adminResponse.setSuccess(true);
            adminResponse.setMessage("User banned Successfully!!");
        }
        return adminResponse;
    }
}
