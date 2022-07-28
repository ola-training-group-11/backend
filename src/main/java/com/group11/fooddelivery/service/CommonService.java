package com.group11.fooddelivery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.group11.fooddelivery.clients.AuthenticationClient;
import com.group11.fooddelivery.configure.Constants;
import com.group11.fooddelivery.model.User;
import com.group11.fooddelivery.model.request.EditProfileRequest;
import com.group11.fooddelivery.model.request.GetProfileRequest;
import com.group11.fooddelivery.model.request.LoginRequest;
import com.group11.fooddelivery.model.request.SignOutRequest;
import com.group11.fooddelivery.model.response.*;
import com.group11.fooddelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommonService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationClient authenticationClient;

    String pepper = "SomethingIsHappening";

    public LoginResponse authenticate(LoginRequest loginRequest) {
        User presentUser = userRepository.findByEmail(loginRequest.getEmail());
        LoginResponse loginResponse = new LoginResponse();
        if (presentUser == null) {
            loginResponse.setSuccess(false);
            loginResponse.setMessage("User Not Found! Invalid Email! Please Signup first");
        } else {
            String hashedPassword = BCrypt.hashpw(loginRequest.getPassword() + pepper, presentUser.getSalt());
            if (hashedPassword.equals(presentUser.getPassword())) {
                if (!presentUser.isActive()) {
                    loginResponse.setSuccess(false);
                    loginResponse.setMessage("User is banned.");
                } else {
                    loginResponse.setSuccess(true);
                    loginResponse.setMessage("Login Successful!!");
                    UUID uuid =UUID.randomUUID();         //Adding token to the db.
                    presentUser.setToken(uuid.toString());
                    userRepository.save(presentUser);
                }
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

        //Verify session token.
        if(!authenticationClient.verifyToken(getProfileRequest, getProfileRequest.getEmail()))  {
            getProfileResponse.setSuccess(false);
            getProfileResponse.setMessage("User session expired.");
            return getProfileResponse;
        }

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
        getProfileResponse.setSuccess(true);
        getProfileResponse.setMessage("User found!");
        return getProfileResponse;
    }

    public EditProfileResponse editProfile(EditProfileRequest editProfileRequest) {
        EditProfileResponse editProfileResponse = new EditProfileResponse();

        //Verify session token.
        if(!authenticationClient.verifyToken(editProfileRequest, editProfileRequest.getEmail()))  {
            editProfileResponse.setSuccess(false);
            editProfileResponse.setMessage("User session expired.");
            return editProfileResponse;
        }

        User user = userRepository.findByEmail(editProfileRequest.getEmail());
        if (Constants.name.equals(editProfileRequest.getField())) {
            //Change name
            user.setName(editProfileRequest.getNewValue());
        } else if (Constants.password.equals(editProfileRequest.getField())) {
            //Change password
            user.setPassword(editProfileRequest.getNewValue());
        }
        userRepository.save(user);
        editProfileResponse.setSuccess(true);
        editProfileResponse.setMessage(editProfileRequest.getField() + " updated successfully.");
        editProfileResponse.setEmail(editProfileRequest.getEmail());
        editProfileResponse.setField(editProfileRequest.getField());
        editProfileResponse.setNewValue(editProfileRequest.getNewValue());
        return editProfileResponse;
    }
    public SignOutResponse logout(SignOutRequest signOutRequest){
        User currentUser = userRepository.findByEmail(signOutRequest.getEmail());
        SignOutResponse signOutResponse=new SignOutResponse();

        if(currentUser!=null){
            currentUser.setToken(null);
            userRepository.save(currentUser);
            signOutResponse.setSuccess(true);
            signOutResponse.setMessage("Logged out successfully.");
        }
        else{
            signOutResponse.setSuccess(false);
            signOutResponse.setMessage("Something went wrong!!");
        }
        return signOutResponse;

    }
}
