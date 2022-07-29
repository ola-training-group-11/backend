package com.group11.fooddelivery.model.response;

import com.group11.fooddelivery.model.Order;

import java.util.List;

public class GetActiveOrdersManager extends Response{
    List<Order> list;

    public List<Order> getList() {
        return list;
    }

    public void setList(List<Order> list) {
        this.list = list;
    }
}
