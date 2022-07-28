package com.group11.fooddelivery.service;

import com.group11.fooddelivery.model.Item;
import com.group11.fooddelivery.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemByIdService
{
    @Autowired
    ItemRepository itemRepository ;
    public Item getItemById(long itemId)
    {
        return itemRepository.getByItemId(itemId);
    }
}
