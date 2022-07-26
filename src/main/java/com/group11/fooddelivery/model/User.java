package com.group11.fooddelivery.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserInfo")
@Getter
@Setter
public class User {

    @Id
    private long userId;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String password;
    private String role;
    private String salt;
    private String token;
}
