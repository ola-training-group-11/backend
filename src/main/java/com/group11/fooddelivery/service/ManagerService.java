package com.group11.fooddelivery.service;

import com.group11.fooddelivery.model.Order;
import com.group11.fooddelivery.model.OrdersByUser;
import com.group11.fooddelivery.model.Restaurant;
import com.group11.fooddelivery.model.response.GetActiveOrdersManager;
import com.group11.fooddelivery.model.response.UpdateRestaurantDetails;
import com.group11.fooddelivery.model.response.UpdateStatusManagerResponse;
import com.group11.fooddelivery.repository.OrderRepository;
import com.group11.fooddelivery.repository.OrdersByUsersRepository;
import com.group11.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {

    @Autowired
    OrdersByUsersRepository ordersByUsersRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RestaurantRepository restaurantRepository;


    public UpdateStatusManagerResponse updateStatus(String orderId, String status){

        try{
            UpdateStatusManagerResponse updateStatusManagerResponse = new UpdateStatusManagerResponse();
            OrdersByUser ordersByUsers= ordersByUsersRepository.findByOrderId(orderId);

            if(ordersByUsers == null){
                updateStatusManagerResponse.setSuccess(false);
                updateStatusManagerResponse.setMessage("orderId is invalid");
                return updateStatusManagerResponse;
            }

            ordersByUsers.setStatus(status);
            OrdersByUser ordersByUsers1 = ordersByUsersRepository.save(ordersByUsers);

            if(ordersByUsers1 == null){
                updateStatusManagerResponse.setSuccess(false);
                updateStatusManagerResponse.setMessage("status is not update, something went wrong");
                return  updateStatusManagerResponse;
            }

            updateStatusManagerResponse.setSuccess(true);
            updateStatusManagerResponse.setMessage("status is updated");
            return  updateStatusManagerResponse;

        }catch (Exception e){
            UpdateStatusManagerResponse updateStatusManagerResponse = new UpdateStatusManagerResponse();
            updateStatusManagerResponse.setSuccess(false);
            updateStatusManagerResponse.setMessage("Something went wrong");
            return updateStatusManagerResponse;

        }
    }

    public GetActiveOrdersManager getActiveOrders(Long restaurantId){

        try{
            GetActiveOrdersManager getActiveOrdersManager = new GetActiveOrdersManager();
            List<Order> orders= orderRepository.findAllByRestaurantId(restaurantId);

            if(orders == null || orders.size() == 0){
                getActiveOrdersManager.setSuccess(false);
                getActiveOrdersManager.setMessage("No active order is found");
                return getActiveOrdersManager;
            }

            getActiveOrdersManager.setSuccess(true);
            getActiveOrdersManager.setMessage("list of active orders");
            getActiveOrdersManager.setList(orders);

            return  getActiveOrdersManager;

        }catch (Exception e){

            GetActiveOrdersManager getActiveOrdersManager = new GetActiveOrdersManager();
            getActiveOrdersManager.setSuccess(false);
            getActiveOrdersManager.setMessage("Something went wrong");
            return getActiveOrdersManager;

        }
    }

    public UpdateRestaurantDetails updateRestaurantDetails(Restaurant restaurant){

        try{
            UpdateRestaurantDetails updateRestaurantDetails = new UpdateRestaurantDetails();
            Restaurant newRestaurant = restaurantRepository.findByRestaurantId(restaurant.getRestaurantId());

            if(restaurant == null){
                updateRestaurantDetails.setSuccess(false);
                updateRestaurantDetails.setMessage("Invalid restaurant Id");
                return updateRestaurantDetails;
            }

            if(restaurantRepository.save(restaurant) == null){
                updateRestaurantDetails.setSuccess(false);
                updateRestaurantDetails.setMessage("restaurant details not updated, something went wrong");
                return updateRestaurantDetails;
            }

            updateRestaurantDetails.setSuccess(true);
            updateRestaurantDetails.setMessage("restaurant details are updated");

            return  updateRestaurantDetails;

        }catch (Exception e){
            UpdateRestaurantDetails updateRestaurantDetails = new UpdateRestaurantDetails();
            updateRestaurantDetails.setSuccess(false);
            updateRestaurantDetails.setMessage("Something went wrong");
            return updateRestaurantDetails;

        }
    }
}
