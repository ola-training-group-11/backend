package com.group11.fooddelivery.repository;

import com.group11.fooddelivery.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
//List<Item> findAllByrestaurantId(long id);
Item findByitemId(long id);
}
