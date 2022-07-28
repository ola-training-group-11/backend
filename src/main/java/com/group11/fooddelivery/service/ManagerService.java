package com.group11.fooddelivery.service;


import com.group11.fooddelivery.model.*;
import com.group11.fooddelivery.model.request.AddItemRequest;
import com.group11.fooddelivery.model.response.*;
import com.group11.fooddelivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {

    @Autowired
    OrdersByUsersRepository ordersByUsersRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemsByRestaurantRepository itemsByRestaurantRepository;

    public UpdateStatusManagerResponse updateStatus(String orderId, String status) {

        try {
            UpdateStatusManagerResponse updateStatusManagerResponse = new UpdateStatusManagerResponse();
            OrdersByUser ordersByUsers = ordersByUsersRepository.findByOrderId(orderId);

            if (ordersByUsers == null) {
                updateStatusManagerResponse.setSuccess(false);
                updateStatusManagerResponse.setMessage("orderId is invalid");
                return updateStatusManagerResponse;
            }

            ordersByUsers.setStatus(status);
            OrdersByUser ordersByUsers1 = ordersByUsersRepository.save(ordersByUsers);

            if (ordersByUsers1 == null) {
                updateStatusManagerResponse.setSuccess(false);
                updateStatusManagerResponse.setMessage("status is not update, something went wrong");
                return updateStatusManagerResponse;
            }

            updateStatusManagerResponse.setSuccess(true);
            updateStatusManagerResponse.setMessage("status is updated");
            return updateStatusManagerResponse;

        } catch (Exception e) {
            UpdateStatusManagerResponse updateStatusManagerResponse = new UpdateStatusManagerResponse();
            updateStatusManagerResponse.setSuccess(false);
            updateStatusManagerResponse.setMessage("Something went wrong");
            return updateStatusManagerResponse;

        }
    }

    public GetActiveOrdersManager getActiveOrders(Long restaurantId) {

        try {
            GetActiveOrdersManager getActiveOrdersManager = new GetActiveOrdersManager();
            List<Order> orders = orderRepository.findAllByRestaurantId(restaurantId);

            if (orders == null || orders.size() == 0) {
                getActiveOrdersManager.setSuccess(false);
                getActiveOrdersManager.setMessage("No active order is found");
                return getActiveOrdersManager;
            }

            getActiveOrdersManager.setSuccess(true);
            getActiveOrdersManager.setMessage("list of active orders");
            getActiveOrdersManager.setList(orders);

            return getActiveOrdersManager;
        } catch (Exception e) {
            GetActiveOrdersManager getActiveOrdersManager = new GetActiveOrdersManager();
            getActiveOrdersManager.setSuccess(false);
            getActiveOrdersManager.setMessage("Something went wrong");
            return getActiveOrdersManager;

        }
    }

    public UpdateRestaurantDetails updateRestaurantDetails(Restaurant restaurant) {
        try {
            UpdateRestaurantDetails updateRestaurantDetails = new UpdateRestaurantDetails();
            Restaurant newRestaurant = restaurantRepository.findByrestaurantId(restaurant.getRestaurantId());

            if (newRestaurant == null) {
                updateRestaurantDetails.setSuccess(false);
                updateRestaurantDetails.setMessage("Invalid restaurant Id");
                return updateRestaurantDetails;
            }

            if (restaurantRepository.save(restaurant) == null) {
                updateRestaurantDetails.setSuccess(false);
                updateRestaurantDetails.setMessage("restaurant details not updated, something went wrong");
                return updateRestaurantDetails;
            }

            updateRestaurantDetails.setSuccess(true);
            updateRestaurantDetails.setMessage("restaurant details are updated");

            return updateRestaurantDetails;

        } catch (Exception e) {
            UpdateRestaurantDetails updateRestaurantDetails = new UpdateRestaurantDetails();
            updateRestaurantDetails.setSuccess(false);
            updateRestaurantDetails.setMessage("Something went wrong");
            return updateRestaurantDetails;
        }
    }

    // ADD ITEM
    public AddItemResponse addItem(AddItemRequest addItemRequest) {
        AddItemResponse addItemResponse = new AddItemResponse();

        Item currentItem = itemRepository.findByitemId(addItemRequest.getItem().getItemId());
        if (currentItem == null) {
            itemRepository.save(addItemRequest.getItem());
            ItemsByRestaurant itemsByRestaurant = new ItemsByRestaurant();
            itemsByRestaurant.setItemId(addItemRequest.getItem().getItemId());
            itemsByRestaurant.setRestaurantId(addItemRequest.getRestaurantId());
            itemsByRestaurantRepository.save(itemsByRestaurant);
            addItemResponse.setMessage("Item Added");
            addItemResponse.setSuccess(true);
        } else {
            addItemResponse.setSuccess(false);
            addItemResponse.setMessage("Item already Exists");
        }
        return addItemResponse;
    }

    // EDIT ITEM
    public EditItemResponse editItem(Item item) {
        EditItemResponse editItemResponse = new EditItemResponse();
        Item currentItem = itemRepository.findByitemId(item.getItemId());
        if (currentItem == null) {
            editItemResponse.setSuccess(false);
            editItemResponse.setMessage("Item doesn't Exist");
        } else {
            currentItem.setName(item.getName());
            currentItem.setVeg(item.isVeg());
            currentItem.setPrice(item.getPrice());
            currentItem.setDescription(item.getDescription());
            itemRepository.save(currentItem);
            editItemResponse.setSuccess(true);
            editItemResponse.setMessage("Updated Successfully");
        }
        return editItemResponse;
    }

    public GetItemResponse getAllItems(long id) {
        GetItemResponse getItemResponse = new GetItemResponse();
        List<ItemsByRestaurant> itemIdList = itemsByRestaurantRepository.findAllByRestaurantId(id);
        if (itemIdList.isEmpty()) {
            getItemResponse.setAllItems(null);
            getItemResponse.setSuccess(false);
            getItemResponse.setMessage("No Item exists for this restaurant");
        } else {
            List<Item> resultList = new ArrayList<>();
            for (ItemsByRestaurant i : itemIdList) {
                resultList.add(itemRepository.findByitemId(i.getItemId()));
            }
            getItemResponse.setAllItems(resultList);
            getItemResponse.setSuccess(true);
            getItemResponse.setMessage("Item Fetched");
        }
        return getItemResponse;
    }
}
