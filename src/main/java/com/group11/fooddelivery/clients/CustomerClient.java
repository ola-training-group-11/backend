package com.group11.fooddelivery.clients;

import com.group11.fooddelivery.model.Item;
import com.group11.fooddelivery.model.Order;
import com.group11.fooddelivery.model.PlaceOrderRequestItems;
import com.group11.fooddelivery.model.PlaceOrderResponseItems;
import com.group11.fooddelivery.repository.ItemRepository;
import com.group11.fooddelivery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerClient {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderRepository orderRepository;

    public List<PlaceOrderResponseItems> buildSelectedItems(List<PlaceOrderRequestItems> placeOrderRequestItemsList) {
        List<PlaceOrderResponseItems> placeOrderResponseItemsList = new ArrayList<>();
        for(PlaceOrderRequestItems placeOrderRequestItems: placeOrderRequestItemsList)  {
            PlaceOrderResponseItems placeOrderResponseItems = new PlaceOrderResponseItems();
            int quantity = placeOrderRequestItems.getQuantity();
            long itemId = placeOrderRequestItems.getItemId();

            Item item = itemRepository.findById(itemId).orElse(null);
            assert item != null;
            placeOrderResponseItems.setName(item.getName());
            placeOrderResponseItems.setQuantity(quantity);
            placeOrderResponseItems.setPrice(item.getPrice()*quantity);
            placeOrderResponseItemsList.add(placeOrderResponseItems);
        }
        return placeOrderResponseItemsList;
    }

    public int calculateTotalPrice(List<PlaceOrderRequestItems> selectedItems) {
        int totalPrice = 0;
        for(PlaceOrderRequestItems orderedItems:selectedItems)    {
            long itemId = orderedItems.getItemId();
            int quantity = orderedItems.getQuantity();
            Item item = itemRepository.findById(itemId).orElse(null);
            assert item != null;
            int price = item.getPrice();
            totalPrice = totalPrice + quantity * price;


        }
        return totalPrice;
    }

    public void populateOrderInfo(Order order, List<PlaceOrderRequestItems> selectedItems) {
        for(PlaceOrderRequestItems placeOrderRequestItems:selectedItems)  {
            order.setItemId(placeOrderRequestItems.getItemId());
            order.setQuantity(placeOrderRequestItems.getQuantity());
            orderRepository.save(order);
        }
    }
}
