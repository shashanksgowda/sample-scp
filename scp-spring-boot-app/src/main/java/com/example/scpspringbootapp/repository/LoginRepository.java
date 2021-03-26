package com.example.scpspringbootapp.repository;


import com.example.scpspringbootapp.document.Login;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends MongoRepository<Login, String> {

    @Query(" { 'username' : ?0 } ")
    Login getLoginByUsername(String username);

    @Query( " { 'username' : ?0, 'password' : ?1} " )
    Login login (String username, String password);
}
