package com.example.scpspringbootapp.service;

import com.example.scpspringbootapp.document.Login;
import com.example.scpspringbootapp.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    //get
    public Login getLogin(String username){
        return loginRepository.getLoginByUsername(username);
    }

    //add
    public void addLogin(Login login){
        loginRepository.save(login);
    }

    //doLogin
    public Login login(String username, String password) { return loginRepository.login(username, password);}
}
