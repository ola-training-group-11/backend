package com.group11.fooddelivery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.group11.fooddelivery.model.User;
import com.group11.fooddelivery.model.request.GetProfileRequest;
import com.group11.fooddelivery.model.request.LoginRequest;
import com.group11.fooddelivery.model.response.GetProfileResponse;
import com.group11.fooddelivery.model.response.LoginResponse;
import com.group11.fooddelivery.model.response.SignUpResponse;
import com.group11.fooddelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
    @Autowired
    UserRepository userRepository;

    String pepper = "SomethingIsHappening";

    public LoginResponse authenticate(LoginRequest loginRequest) {
        User presentUser = userRepository.findByEmail(loginRequest.getUserName());
        LoginResponse loginResponse = new LoginResponse();
        if (presentUser == null) {
            loginResponse.setSuccess(false);
            loginResponse.setMessage("User Not Found! Invalid Email! Please Signup first");
        } else {
            String hashedPassword = BCrypt.hashpw(loginRequest.getPassword() + pepper, presentUser.getSalt());
            if (hashedPassword.equals(presentUser.getPassword())) {
                loginResponse.setSuccess(true);
                loginResponse.setMessage("Login Successful!!");
            } else {
                loginResponse.setSuccess(false);
                loginResponse.setMessage("User Name or Password is incorrect!! Please enter correct credentials!!");
            }
        }
        return loginResponse;
    }

    public SignUpResponse register(User user) {
        User currentUser = userRepository.findByEmail(user.getEmail());

        SignUpResponse signUpResponse = new SignUpResponse();
        if (currentUser != null && currentUser.getEmail().equals(user.getEmail())) {
            signUpResponse.setSuccess(false);
            signUpResponse.setMessage("User Already Exists!! Please login!!");
        } else {
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(user.getPassword() + pepper, salt);

            user.setPassword(hashedPassword);
            user.setSalt(salt);

            userRepository.save(user);
            signUpResponse.setSuccess(true);
            signUpResponse.setMessage("SignUp Successful!! Please login to Order Food!! ");
        }
        return signUpResponse;
    }

    public GetProfileResponse getProfile(GetProfileRequest getProfileRequest) {
        GetProfileResponse getProfileResponse = new GetProfileResponse();
        String email = getProfileRequest.getEmail();
        User user = userRepository.findByEmail(email);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        getProfileResponse.setProfile(json);
        return getProfileResponse;
    }
}
