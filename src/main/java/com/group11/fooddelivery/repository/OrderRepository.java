package com.group11.fooddelivery.repository;

import com.group11.fooddelivery.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

}
