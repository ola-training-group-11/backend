package com.group11.fooddelivery.repository;

import com.group11.fooddelivery.model.ItemsByRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsByRestaurantRepository extends JpaRepository<ItemsByRestaurant, Long> {

}
