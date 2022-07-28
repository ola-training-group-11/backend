package com.group11.fooddelivery.repository;

import com.group11.fooddelivery.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item getByItemId(long item_id);
}
