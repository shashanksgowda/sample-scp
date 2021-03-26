package com.example.scpspringbootapp.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "login")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    private String id;
    private String username;
    private String password;
}
