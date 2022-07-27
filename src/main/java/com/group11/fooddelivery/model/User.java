package com.group11.fooddelivery.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserInfo")
@Getter
@Setter

public class User {

    @Id
    private String email;
    @NonNull
    private String name;
    @NonNull
    private String password;
    private String role;
    private String salt;
    private String token;
    private boolean isActive = true; //if false, then user is banned.
}
