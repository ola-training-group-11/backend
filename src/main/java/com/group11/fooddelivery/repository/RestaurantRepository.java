package com.group11.fooddelivery.repository;

import com.group11.fooddelivery.model.OrdersDetails;
import com.group11.fooddelivery.model.OrdersByUsers;
import com.group11.fooddelivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<OrdersByUsers, Long> {
    OrdersByUsers findByorderId(String orderId);
    List<OrdersDetails> findByrestaurantId(String restaurantId);
}

