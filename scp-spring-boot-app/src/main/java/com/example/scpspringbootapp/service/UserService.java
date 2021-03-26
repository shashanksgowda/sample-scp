package com.example.scpspringbootapp.service;

import com.example.scpspringbootapp.document.Login;
import com.example.scpspringbootapp.document.User;
import com.example.scpspringbootapp.exception.UserValidationException;
import com.example.scpspringbootapp.repository.UserRepository;
import com.example.scpspringbootapp.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    //add
    public void addUser(User user, Login login) throws UserValidationException{
        validateUserDetails(user, login, true);
        userRepository.insert(user);
        loginService.addLogin(login);
    }

    //update
    public void updateUser(User newUser) throws UserValidationException{
        User user = getUser(newUser.getUsername());
        if(user == null){
            throw new UserValidationException("User record not found!");
        }
        user.setId(newUser.getId());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        userRepository.save(user);
    }

    //delete
    public void deleteUser(String username) throws UserValidationException {
        User user = getUser(username);
        if(user == null){
            throw new UserValidationException("User record not found!");
        }
        userRepository.delete(user);
    }

    //get
    public User getUser(String username){
        return userRepository.findByUsername(username);
    }

    //validate user details
    public void validateUserDetails(User user, Login login, boolean add) throws UserValidationException{
        if(user == null){
            throw new UserValidationException("Cannot persist this record!");
        }
        if(StringUtil.isEmpty(user.getUsername())){
            throw new UserValidationException("Username cannot be empty");
        }
        Login tmpLogin = loginService.getLogin(user.getUsername());

        if (tmpLogin != null && add) {
            throw new UserValidationException("Username already exists");
        }
        if(tmpLogin == null && !add){
            throw new UserValidationException("Invalid username or password");
        }
        if(StringUtil.isEmpty(login.getPassword())){
            throw new UserValidationException("Password cannot be empty");
        }
    }
}
