package com.group11.fooddelivery.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Entity
@Table(name = "OrderInfo")
@Getter
@Setter
public class Order {
    @Id
    private String orderId = UUID.randomUUID().toString();
    private long restaurantId;
    private String itemId;
    private String quantity;
}
