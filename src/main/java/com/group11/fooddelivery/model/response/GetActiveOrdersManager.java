package com.group11.fooddelivery.model.response;

import com.group11.fooddelivery.model.OrdersDetails;

import java.util.List;

public class GetActiveOrdersManager extends Response{
    List<OrdersDetails> list;

    public List<OrdersDetails> getList() {
        return list;
    }

    public void setList(List<OrdersDetails> list) {
        this.list = list;
    }
}
