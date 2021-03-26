package com.example.scpspringbootapp.controller;

import com.example.scpspringbootapp.document.Login;
import com.example.scpspringbootapp.document.User;
import com.example.scpspringbootapp.exception.UserValidationException;
import com.example.scpspringbootapp.service.LoginService;
import com.example.scpspringbootapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/user")
    public ResponseEntity<String> getUser(@RequestParam String username){
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (userService.getUser(username) == null) {
                return ResponseEntity.ok(mapper.writeValueAsString(userService.getUser(username)));
            }
            return ResponseEntity.ok(mapper.writeValueAsString(userService.getUser(username)));
        }catch (Exception ex){
            return new ResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestBody Map<String, Map<String, String>> userDetails){
        try {
            Map<String, String> userDetail = userDetails.get("user");
            Map<String, String> credentials = userDetails.get("credential");
            User user = new User();
            user.setUsername(userDetail.get("username"));
            user.setFirstName(userDetail.get("firstName"));
            user.setLastName(userDetail.get("lastName"));


            Login login = new Login();
            login.setUsername(user.getUsername());
            login.setPassword(credentials.get("password"));

            userService.addUser(user, login);
            loginService.addLogin(login);

            return new ResponseEntity<>("User has been added", HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/user/signin")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials){

        String username = credentials.get("username");
        String password = credentials.get("password");
        Login login = loginService.login(username, password);
        if(login == null){
            return new ResponseEntity<>("cannot login", HttpStatus.UNAUTHORIZED);
        } else {
            return ResponseEntity.ok("valid user");
        }
    }

    @PostMapping("/user/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> userDetails){
        String firstName = userDetails.get("firstName");
        String lastName = userDetails.get("lastName");
        String username = userDetails.get("username");
        String password = userDetails.get("password");

        if(userService.getUser(username) == null){
            User newUser = new User();
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setUsername(username);

            Login login = new Login();
            login.setUsername(username);
            login.setPassword(password);

            try {
                userService.addUser(newUser, login);
                return ResponseEntity.ok("Signup Successful");
            } catch (UserValidationException ex) {
                return new ResponseEntity<>("Error During Signup", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Username Already exists! Choose another username", HttpStatus.BAD_REQUEST);
    }
}
