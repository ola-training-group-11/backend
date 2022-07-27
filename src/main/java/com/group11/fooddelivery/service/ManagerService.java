package com.group11.fooddelivery.service;

import com.group11.fooddelivery.model.OrdersDetails;
import com.group11.fooddelivery.model.OrdersByUsers;
import com.group11.fooddelivery.model.Restaurant;
import com.group11.fooddelivery.model.response.GetActiveOrdersManager;
import com.group11.fooddelivery.model.response.UpdateRestaurantDetails;
import com.group11.fooddelivery.model.response.UpdateStatusManagerResponse;
import com.group11.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {

    @Autowired
    RestaurantRepository restaurantRepository;

    public UpdateStatusManagerResponse updateStatus(String orderId, String status){

        try{
            UpdateStatusManagerResponse updateStatusManagerResponse = new UpdateStatusManagerResponse();
            OrdersByUsers ordersByUsers= restaurantRepository.findByorderId(orderId);

            if(ordersByUsers == null){
                updateStatusManagerResponse.setSuccess(false);
                updateStatusManagerResponse.setMessage("orderId is invalid");
                return updateStatusManagerResponse;
            }

            ordersByUsers.setStatus(status);
            OrdersByUsers ordersByUsers1 = restaurantRepository.save(ordersByUsers);

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

    public GetActiveOrdersManager getActiveOrders(String restaurantId){

        try{
            GetActiveOrdersManager getActiveOrdersManager = new GetActiveOrdersManager();
            List<OrdersDetails> ordersDetails = (List<OrdersDetails>) restaurantRepository.findByrestaurantId(restaurantId);

            if(ordersDetails == null || ordersDetails.size() == 0){
                getActiveOrdersManager.setSuccess(false);
                getActiveOrdersManager.setMessage("No active order is found");
                return getActiveOrdersManager;
            }

            getActiveOrdersManager.setSuccess(true);
            getActiveOrdersManager.setMessage("status is updated");
            getActiveOrdersManager.setList(ordersDetails);

            return  getActiveOrdersManager;

        }catch (Exception e){
            GetActiveOrdersManager getActiveOrdersManager = new GetActiveOrdersManager();
            getActiveOrdersManager.setSuccess(false);
            getActiveOrdersManager.setMessage("Something went wrong");
            return getActiveOrdersManager;

        }
    }

    public UpdateRestaurantDetails updateRestaurantDetails(String restaurantId){

        try{
            UpdateRestaurantDetails updateRestaurantDetails = new UpdateRestaurantDetails();
            Restaurant restaurant = restaurantRepository.findByrestaurantId(restaurantId);

            if(ordersDetails == null || ordersDetails.size() == 0){
                getActiveOrdersManager.setSuccess(false);
                getActiveOrdersManager.setMessage("No active order is found");
                return getActiveOrdersManager;
            }

            getActiveOrdersManager.setSuccess(true);
            getActiveOrdersManager.setMessage("status is updated");
            getActiveOrdersManager.setList(ordersDetails);

            return  getActiveOrdersManager;

        }catch (Exception e){
            GetActiveOrdersManager getActiveOrdersManager = new GetActiveOrdersManager();
            getActiveOrdersManager.setSuccess(false);
            getActiveOrdersManager.setMessage("Something went wrong");
            return getActiveOrdersManager;

        }
    }
}
