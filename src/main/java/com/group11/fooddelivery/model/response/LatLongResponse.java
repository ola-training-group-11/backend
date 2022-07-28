package com.group11.fooddelivery.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LatLongResponse extends Response{

    private List<String> ListOfRestaurant;

}
