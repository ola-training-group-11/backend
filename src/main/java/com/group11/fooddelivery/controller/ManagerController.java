package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.Item;
import com.group11.fooddelivery.model.request.AddItemRequest;
import com.group11.fooddelivery.model.response.AddItemResponse;
import com.group11.fooddelivery.model.response.EditItemResponse;
import com.group11.fooddelivery.model.response.GetItemResponse;
import com.group11.fooddelivery.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManagerController {
    @Autowired
    ManagerService managerService;
 GetItemResponse getItemResponse = new GetItemResponse();
    AddItemResponse addItemResponse = new AddItemResponse();
    @GetMapping(value="/getItems/{id}", produces="application/json")
    public GetItemResponse get_items(@PathVariable long id){
           getItemResponse = managerService.getAllItems(id);
           return getItemResponse;
       }

    @PostMapping(value="/addItem",consumes = "application/json",produces="application/json")
    public AddItemResponse AddItem(@RequestBody AddItemRequest addItemRequest){
        addItemResponse = managerService.addItem(addItemRequest);
        return addItemResponse;
    }

    EditItemResponse editItemResponse = new EditItemResponse();
    @PostMapping(value="/editItem",consumes = "application/json",produces="application/json")
    public EditItemResponse EditItem(@RequestBody Item item){
        editItemResponse = managerService.editItem(item);
        return editItemResponse;
    }
}
