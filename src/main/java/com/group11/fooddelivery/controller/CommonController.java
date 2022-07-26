package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.request.LoginRequest;
import com.group11.fooddelivery.model.User;
import com.group11.fooddelivery.model.response.LoginResponse;
import com.group11.fooddelivery.model.response.SignUpResponse;
import com.group11.fooddelivery.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommonController {
    @Autowired
    CommonService commonService;

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        LoginResponse loginResponse = commonService.authenticate(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/signup", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SignUpResponse> signUp(User user) {
        SignUpResponse signUpResponse = commonService.register(user);
        return new ResponseEntity<>(signUpResponse, HttpStatus.OK);
    }
}
