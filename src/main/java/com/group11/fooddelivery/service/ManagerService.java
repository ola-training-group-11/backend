package com.group11.fooddelivery.service;

import com.group11.fooddelivery.model.Item;
import com.group11.fooddelivery.model.ItemsByRestaurant;
import com.group11.fooddelivery.model.request.AddItemRequest;
import com.group11.fooddelivery.model.response.AddItemResponse;
import com.group11.fooddelivery.model.response.EditItemResponse;
import com.group11.fooddelivery.model.response.GetItemResponse;
import com.group11.fooddelivery.repository.ItemRepository;
import com.group11.fooddelivery.repository.ItemsByRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemsByRestaurantRepository itemsByRestaurantRepository;


    AddItemResponse addItemResponse = new AddItemResponse();
    EditItemResponse editItemResponse = new EditItemResponse();

                      // ADD ITEM
    public AddItemResponse addItem(AddItemRequest addItemRequest){
       Item currentItem = itemRepository.findByitemId(addItemRequest.getItem().getItemId());
       if(currentItem == null){
           itemRepository.save(addItemRequest.getItem());
           ItemsByRestaurant itemsByRestaurant = new ItemsByRestaurant();
           itemsByRestaurant.setItemId(addItemRequest.getItem().getItemId());
           itemsByRestaurant.setRestaurantId(addItemRequest.getRestaurantId());
           itemsByRestaurantRepository.save(itemsByRestaurant);
           addItemResponse.setMessage("Item Added");
           addItemResponse.setSuccess(true);
       }else {
           addItemResponse.setSuccess(false);
           addItemResponse.setMessage("Item already Exists");
       }
       return addItemResponse;
   }

                        // EDIT ITEM
   public EditItemResponse editItem(Item item){
       Item currentItem = itemRepository.findByitemId(item.getItemId());
       if(currentItem == null){
           editItemResponse.setSuccess(false);
           editItemResponse.setMessage("Item doesn't Exist");
       }else{
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

                         //GET ALL ITEM
    GetItemResponse getItemResponse = new GetItemResponse();
   public GetItemResponse getAllItems(long id){
         List<ItemsByRestaurant> itemIdList = itemsByRestaurantRepository.findAllByRestaurantId(id);
         if(itemIdList.isEmpty()){
             getItemResponse.setAllItems(null);
             getItemResponse.setSuccess(false);
             getItemResponse.setMessage("No Item exists for this restaurant");
         }else{
              List<Item> resultList = new ArrayList<>();
              for(ItemsByRestaurant i:itemIdList){
                  resultList.add(itemRepository.findByitemId(i.getItemId()));
              }
              getItemResponse.setAllItems(resultList);
              getItemResponse.setSuccess(true);
              getItemResponse.setMessage("Item Fetched");
             }
         return getItemResponse;
         }

    }

